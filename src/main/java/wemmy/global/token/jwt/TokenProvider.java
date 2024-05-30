package wemmy.global.token.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.TokenValidateException;
import wemmy.global.token.jwt.constant.TokenType;
import wemmy.global.token.jwt.dto.TokenDto;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public TokenDto createToken(Long id, String email, String userRole) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(id, email, userRole, accessTokenExpireTime);
        String refreshToken = createRefreshToken(id, refreshTokenExpireTime);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        // 현재시간 + 10m
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        // 현재시간 + 24h
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long id, String email, String userRole, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())  // 토큰 제목
                .setExpiration(expirationTime)        // 만료 시간
                .claim("id", id)                // 회원 id ( DB PK )
                .claim("email", email)          // 회원 email
                .claim("userRole", userRole)    // userRole
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long id, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())  // 토큰 제목
                .setExpiration(expirationTime)        // 만료 시간
                .claim("id", id)                // 회원 id ( DB PK )
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .compact();

        return refreshToken;
    }

    // 토큰 검증
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.info("token expired");
            throw new TokenValidateException(ErrorCode.TOKEN_EXPIRED);

        } catch (Exception e) {
            log.info("잘못된 토큰");
            throw new TokenValidateException(ErrorCode.NOT_VALID_TOKEN);
        }
        return true;
    }

    public Claims getTokenClaims(String token) {

        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.info("잘못된 토큰");
            throw new TokenValidateException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

    public Long getUserIdFromClaims(String token){
        return Long.parseLong(getTokenClaims(token).get("id").toString());
    }
}
