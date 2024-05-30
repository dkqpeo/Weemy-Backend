package wemmy.api.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.global.token.jwt.dto.AccessTokenResponseDTO;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.service.token.TokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy")
public class TokenController {

    private final TokenService tokenService;

    /**
     * 엑세스 토큰 재발급 처리
     * @param httpServletRequest  refresh_token
     * @return access_token
     */

    @PostMapping("/access-token/reissue")
    public ResponseEntity<AccessTokenResponseDTO> reissueToken(HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];

        AccessTokenResponseDTO accessToken = tokenService.createAccessToken(refreshToken);

        return ResponseEntity.ok(accessToken);
    }
}
