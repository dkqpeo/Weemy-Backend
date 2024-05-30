package wemmy.domain.user.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserType {
    LOCAL, KAKAO, APPLE;

    public static UserType from(String type) {
        return UserType.valueOf(type);
    }

    public static boolean isUserType(String type) {
        List<UserType> userTypes = Arrays.stream(UserType.values())
                .filter(userType -> userType.name().equals(type))
                .collect(Collectors.toList());

        return userTypes.size() != 0;
    }
}
