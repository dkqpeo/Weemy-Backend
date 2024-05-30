package wemmy.global.token.oauth.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import wemmy.domain.user.constant.UserType;
import wemmy.global.token.oauth.kakao.client.KakaoUserInfoClient;
import wemmy.global.token.oauth.kakao.dto.KakaoUserInfoRespDTO;
import wemmy.global.token.oauth.model.OAuthAttributes;
import wemmy.global.token.oauth.service.SocialLoginApiService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiService implements SocialLoginApiService {

    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final String CONTENT_TYPE = "application/w-xxx-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        // AccessToken의 헤더에 BEARER 담기
        // User 정보 조회
        KakaoUserInfoRespDTO kakaoUserInfoResponseDto =
                kakaoUserInfoClient.getKakaoUserInfo(CONTENT_TYPE, "Bearer "+accessToken);

        // 조회한 User 정보에서 email 추출
        KakaoUserInfoRespDTO.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount();
        String email = kakaoAccount.getEmail();

        // email이 비어있다면 id를 담기
        return OAuthAttributes.builder()
                .email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDto.getId() : email)
                .userType(UserType.KAKAO)
                .build();
    }
}
