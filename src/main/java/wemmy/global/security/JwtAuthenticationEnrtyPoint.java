package wemmy.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationEnrtyPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.error("인증 실패 : {}", authException.getMessage());

        // Request에 설정된 예외 정보 확인
        String exception = (String) request.getAttribute("exception");

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 401);
        errorResponse.put("error", "Unauthorized");

        if (exception != null) {
            switch (exception) {
                case "EXPIRED_TOKEN":
                    errorResponse.put("errorCode", "A-001");
                    errorResponse.put("message", "토큰이 만료되었습니다.");
                    break;
                case "INVALID_TOKEN":
                    errorResponse.put("errorCode", "A-002");
                    errorResponse.put("message", "해당 토큰은 유효한 토큰이 아닙니다.");
                    break;
                default:
                    errorResponse.put("errorCode", "AUTH-001");
                    errorResponse.put("message", "인증에 실패했습니다.");
            }
        } else {
            errorResponse.put("errorCode", "AUTH-001");
            errorResponse.put("message", "인증에 실패했습니다.");
        }

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
