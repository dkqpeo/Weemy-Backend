package wemmy.api.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.user.LoginDTO;
import wemmy.dto.user.OAuthLoginDTO;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.global.validate.OAuthValidator;
import wemmy.service.user.oauth.OAuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/oauth")
public class OAuthLoginController {

    private final OAuthService oAuthService;
    private final OAuthValidator oAuthValidator;

    /**
     * 카카오 로그인 api 인가코드를 받기 위해 사용
     * 앱 구현 시 이 부분은 앱에서 적용
     * 코드 얻는 url : https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=234744b67b5d99fa07dd07f298908946&redirect_uri=http://localhost:8080/oauth2/kakao
     * 코드 얻는 url : https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=234744b67b5d99fa07dd07f298908946&redirect_uri=http://localhost:8080/wemmy/user/oauth/kakao
     * @param code
     * @return void
     */

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam("code") String code){
        System.out.println(code);
        String accessToken = oAuthService.getKakaoAccessToken(code);

    }

    /**
     * 카카오에서 얻은 엑세스 토큰으로 서버 접근을 위한 엑세스토큰 요청
     * @param oAuthLoginDTO  // userType ex) KAKAO, APPLE
     * @param httpServletRequest  // header : kakao_accessToken
     * @return  // accessToken, refreshToken
     */
    @PostMapping("/login")
    public ResponseEntity<LoginDTO.response> login(@RequestBody OAuthLoginDTO oAuthLoginDTO,
                                                   HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");

        log.info(authorization.split(" ")[0]);
        log.info(authorization.split(" ")[1]);
        UserType userType = UserType.from(oAuthLoginDTO.getUserType());
        log.info(String.valueOf(userType));

        // 헤더 필수값 체크
        AuthorizationHeaderUtils.validateAuthorization(authorization);
        oAuthValidator.validateUserType(oAuthLoginDTO.getUserType());

        String accessToken = authorization.split(" ")[1];
        LoginDTO.response result = oAuthService.outhLogin(accessToken, UserType.from(oAuthLoginDTO.getUserType()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
