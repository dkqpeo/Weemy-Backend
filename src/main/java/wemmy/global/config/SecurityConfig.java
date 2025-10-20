package wemmy.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wemmy.global.security.JwtAccessDenideHandler;
import wemmy.global.security.JwtAuthenticationEnrtyPoint;
import wemmy.global.security.JwtAuthenticationFilter;
import wemmy.global.token.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // @PreAuthorize 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime;
    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;
    @Value("${token.secret}")
    private String tokenSecret;

    //private final DefaultOAuth2UserService oAuth2UserService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEnrtyPoint jwtAuthenticationEnrtyPoint;
    private final JwtAccessDenideHandler jwtAccessDenideHandler;

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 예외 처리
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEnrtyPoint)
                        .accessDeniedHandler(jwtAccessDenideHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // 공개 API - 인증 불필요
                        .requestMatchers(
                                "/wemmy/user/v2/signup",
                                "/wemmy/user/v2/sign-up",
                                "/wemmy/user/v2/login",
                                "/wemmy/user/login",
                                "/wemmy/user/validate/**",
                                "/wemmy/user/v2/validate/**",
                                "/wemmy/user/oauth/**",
                                "/wemmy/access-token/reissue",
                                "/wemmy/token/refresh",
                                "/wemmy/benefit/v2/web/**",
                                "/wemmy/benefit/web/**"
                        ).permitAll()

                        // 관리자 API - ADMIN 역할 필요
                        .requestMatchers("/wemmy/admin/**").hasRole("ADMIN")

                        // Actuator (프로덕션에서는 제한)
                        .requestMatchers("/monitering/**").permitAll()

                        // Swagger/OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 나머지 모든 요청 - 인증 필요
                        .anyRequest().authenticated()
                )
                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
