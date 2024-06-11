package wemmy.api.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.global.token.jwt.dto.AccessTokenResponseDTO;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.service.token.TokenService;

@Tag(name = "Token", description = "엑세스 토큰 재발행 API")
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

    @Tag(name = "Token")
    @Operation(summary = "엑세스 토큰 재발행 요청", description = "header에 refresh Token 넣어서 요청.")
    @PostMapping("/access-token/reissue")
    public ResponseEntity<AccessTokenResponseDTO> reissueToken(HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];
        AccessTokenResponseDTO accessToken = tokenService.createAccessToken(refreshToken);

        return ResponseEntity.ok(accessToken);
    }
}
