package wemmy.global.validate;

import org.springframework.stereotype.Service;
import wemmy.domain.user.constant.UserType;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.MemberException;

@Service
public class Validator {

    public void validateMemberType(String memberType){
        if (!UserType.isUserType(memberType))
            throw new MemberException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
