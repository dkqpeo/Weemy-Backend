package wemmy.global.token.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GetUserIDByToken {

    private final TokenProvider tokenProvider;

    public Long getUserID(HttpServletRequest httpServletRequest) {
        // 헤더의 토큰 정보 검증
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenProvider.validateToken(accessToken);

        // 토큰의 사용자 id(PK) 조회
        Long userId = tokenProvider.getUserIdFromClaims(accessToken);
        return userId;
    }
}
