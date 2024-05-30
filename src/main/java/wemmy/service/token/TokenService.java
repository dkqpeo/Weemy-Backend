package wemmy.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wemmy.domain.user.UserEntity;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.dto.AccessTokenResponseDTO;
import wemmy.service.user.UserService;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    public AccessTokenResponseDTO createAccessToken(String refreshToken) {

        Optional<UserEntity> user = userService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenProvider.createAccessTokenExpireTime();
        String accessToken = tokenProvider.createAccessToken(user.get().getId(), user.get().getEmail(), accessTokenExpireTime);

        return AccessTokenResponseDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
