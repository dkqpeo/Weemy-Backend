package wemmy.global.token.oauth.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import wemmy.global.token.oauth.kakao.dto.KakaoUserInfoRespDTO;

/**
 *  클라이언트로 부터 받은 소셜 엑세스 토큰으로 사용자 정보 요청
 */
@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserInfoClient")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfoRespDTO getKakaoUserInfo(@RequestHeader("Content-Type") String contentTupe,
                                          @RequestHeader("Authorization") String accessToken);
}
