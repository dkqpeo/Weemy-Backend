package wemmy.service.user.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.user.LoginDTO;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.dto.TokenDto;
import wemmy.global.token.oauth.model.OAuthAttributes;
import wemmy.global.token.oauth.service.SocialLoginApiService;
import wemmy.global.token.oauth.service.SocialLoginApiServiceFactory;
import wemmy.service.user.UserService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OAuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    /**
     * 인가코드로 카카오 api에 엑세스 토큰 요청
     * 앱에서 구현해야 하는 부분 테스트를 위해 사용
     * @param code
     * @return accessToken
     */
    public String getKakaoAccessToken(String code) {

        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // post 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=234744b67b5d99fa07dd07f298908946" + // TODO REST_API_KEY 입력
                    "&redirect_uri=http://localhost:8080/wemmy/user/oauth/kakao" + // TODO 인가코드 받은 redirect_uri 입력
                    "&code=" + code +
                    "&client_secret=qkmRjsgUjed9DuqgceUcXSYqAjU9CPpm";
            bw.write(sb);

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);

            br.close();
            bw.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 소셜 로그인을 통해 얻은 accessToken 검증 및 로그인 처리
     * @param accessToken
     * @param userType
     * @return  accessToken, refreshToken
     */
    public LoginDTO.response outhLogin(String accessToken, UserType userType) {

        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}", userInfo);

        TokenDto tokenDto;

        Optional<UserEntity> findUser = userService.finBydUserEmail(userInfo.getEmail());
        if(findUser.isEmpty()) { // 신규 회원
            // OAuthAttribute 객체를 활용해서 엔티티 생성
            UserEntity oauthUser = userInfo.toUserEntity(userType, Role.USER);
            userService.signUp(oauthUser);

            tokenDto = tokenProvider.createToken(oauthUser.getId(), oauthUser.getEmail(), String.valueOf(oauthUser.getRole()));
            oauthUser.updateToken(tokenDto);
        } else { // 기존회원
            // 토큰 생성
            UserEntity oauthUser = findUser.get();

            tokenDto = tokenProvider.createToken(oauthUser.getId(), oauthUser.getEmail(), String.valueOf(oauthUser.getRole()));
            oauthUser.updateToken(tokenDto);
        }

        return LoginDTO.response.of(tokenDto);
    }
}
