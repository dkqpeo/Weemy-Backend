package wemmy.global.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import wemmy.global.token.jwt.TokenProvider;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 1. Request Header에서  JWT토큰 추출
            String token = resolveToken(request);

            // 2. 토큰 유효성 검증
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

                //3. 토큰에서 Authenticartion 객체 생성
                Authentication authentication = tokenProvider.getAuthentication(token);

                // 4. SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Security Context에 '{}' 인증 정보 저장, uri: {}",
                        authentication.getName(), request.getRequestURI());
            } else
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}", request.getRequestURI());

        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            request.setAttribute("exception", "EXPIRED_TOKEN");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            request.setAttribute("exception", "UNSUPPORTED_TOKEN");
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 토큰입니다.");
            request.setAttribute("exception", "MALFORMED_TOKEN");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            request.setAttribute("exception", "INVALID_TOKEN");
        } catch (Exception e) {
            log.error("JWT 토큰 검증 중 오류 발생", e);
            request.setAttribute("exception", "UNKNOWN_ERROR");
        }

        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
