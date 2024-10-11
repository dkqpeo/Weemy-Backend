package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.user.LoginDTO;
import wemmy.dto.user.OAuthLoginDTO;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.global.validate.OAuthValidator;
import wemmy.service.user.oauth.OAuthService;

@Tag(name = "UserV2", description = "회원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2/oauth")
public class OAuthLoginControllerV2 {

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

    /*@Tag(name = "User")
    @Operation(summary = "서버 테스트용")
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam("code") String code){
        System.out.println(code);
        String accessToken = oAuthService.getKakaoAccessToken(code);

    }*/

    /**
     * 카카오에서 얻은 엑세스 토큰으로 서버 접근을 위한 엑세스토큰 요청
     * @param oAuthLoginDTO  // userType ex) KAKAO, APPLE
     * @param httpServletRequest  // header : kakao_accessToken
     * @return  // accessToken, refreshToken
     */
    @Tag(name = "UserV2")
    @Operation(summary = "사용자 소셜 로그인 API", description = "소셜에서 받은 토큰으로 로그인 처리. userType : KAKAO 와 같이 대문자로 작성.")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO.loginResponse> login(@RequestBody OAuthLoginDTO oAuthLoginDTO,
                                                   HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        String authorization = httpServletRequest.getHeader("Authorization");

        log.info(authorization.split(" ")[0]);
        log.info(authorization.split(" ")[1]);
        UserType userType = UserType.from(oAuthLoginDTO.getUserType());
        log.info(String.valueOf(userType));

        // 헤더 필수값 체크
        AuthorizationHeaderUtils.validateAuthorization(authorization);
        oAuthValidator.validateUserType(oAuthLoginDTO.getUserType());

        String accessToken = authorization.split(" ")[1];
        LoginDTO.loginResponse result = oAuthService.outhLogin(accessToken, UserType.from(oAuthLoginDTO.getUserType()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
