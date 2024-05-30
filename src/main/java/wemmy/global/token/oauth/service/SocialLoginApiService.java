package wemmy.global.token.oauth.service;

import wemmy.global.token.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {  // 소셜에서 회원정보 가져오기

    OAuthAttributes getUserInfo(String accessToken);
}
