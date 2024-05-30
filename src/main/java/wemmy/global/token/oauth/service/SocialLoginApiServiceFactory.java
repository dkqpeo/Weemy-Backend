package wemmy.global.token.oauth.service;

import org.springframework.stereotype.Service;
import wemmy.domain.user.constant.UserType;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    //SocialLoginApiService 구현체가 각각 들어감.
    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        SocialLoginApiServiceFactory.socialLoginApiServices = socialLoginApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(UserType userType) {
        String socialLoginApiServiceBeanName = "";

        if(UserType.KAKAO.equals(userType)) {
            socialLoginApiServiceBeanName = "kakaoLoginApiService";
        }

        // 빈의 이름으로 구현체 추출
        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
