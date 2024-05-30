package wemmy.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wemmy.global.token.jwt.dto.TokenDto;

import java.util.Date;

public class LoginDTO {

    @Getter @Setter
    public static class request {
        private String email;
        private String password;
    }

    @Getter @Builder
    public static class response {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Date accessTokenExpireTime;
        private Date refreshTokenExpireTime;

        public static response of (TokenDto tokenDto) {
            return response.builder()
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }
    }
}
