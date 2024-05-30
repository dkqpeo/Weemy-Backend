package wemmy.global.token.jwt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class AccessTokenResponseDTO {

    private String grantType;
    private String accessToken;
    private Date accessTokenExpireTime;
}
