package wemmy.global.token.jwt.util;

import org.springframework.util.StringUtils;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.TokenValidateException;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        // 1. authorizationHeader 필수값 체크
        if(!StringUtils.hasText(authorizationHeader))
            // 예외 발생
            throw new TokenValidateException(ErrorCode.NOT_EXISTS_AUTHORIZATION);

        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" "); // [0]: Bearer, [1]: accessToken
        if (authorizations.length<2 || (!"Bearer".equals(authorizations[0])))
            // 예외 발생
            throw new TokenValidateException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
    }
}
