package wemmy.global.token.oauth.model;

import lombok.Builder;
import lombok.Getter;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;

import java.time.LocalDateTime;

@Getter @Builder
public class OAuthAttributes {

    // private String name;  // 필요시 사용
    private String email;
    private UserType userType;
    private LocalDateTime createdAt;

    public UserEntityV2 toUserEntity(UserType userType, Role role) {
        return UserEntityV2.builder()
                .userType(userType)
                .email(email)
                .createdAt(LocalDateTime.now())
                .role(role)
                .build();
    }
}
