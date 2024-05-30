package wemmy.global.validate;

import org.springframework.stereotype.Service;
import wemmy.domain.user.constant.UserType;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.MemberException;

@Service
public class OAuthValidator {

    public void validateUserType(String userType){
        if (!UserType.isUserType(userType))
            throw new MemberException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
