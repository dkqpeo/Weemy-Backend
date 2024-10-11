package wemmy.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wemmy.domain.user.UserEntityV2;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.dto.AccessTokenResponseDTO;
import wemmy.service.user.UserServiceV2;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceV2 {

    private final UserServiceV2 userServiceV2;
    private final TokenProvider tokenProvider;

    public AccessTokenResponseDTO createAccessToken(String refreshToken) {

        Optional<UserEntityV2> user = userServiceV2.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenProvider.createAccessTokenExpireTime();
        String accessToken = tokenProvider.createAccessToken(user.get().getId(), user.get().getEmail(), String.valueOf(user.get().getRole()), accessTokenExpireTime);

        return AccessTokenResponseDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
