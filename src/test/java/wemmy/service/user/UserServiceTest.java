package wemmy.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import wemmy.domain.user.UserEntityV2;
import wemmy.global.config.error.exception.MemberException;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    @MockBean
    private UserServiceV2 userServiceV2;

    @Test
    void signUpByAdmin() {

        // 관리자 계정 생성
        try {
            userServiceV2.signUpByAdmin("teamwemmy@gmail.com", "project2024");
        } catch (MemberException e) {

        }
    }

    @Test
    void signUpByAdmin() {

        // 관리자 계정 생성
        try {
            userServiceV2.signUpByAdmin("teamwemmy@gmail.com", "project2024");
        } catch (MemberException e) {

        }

    }

    @Test
    void getUserInfo() {
        UserEntityV2 user = userServiceV2.findByUserId(2L);

        System.out.println(user.getSigg_id().getSido_id().getAdm_code());
    }

    @Test
    void 이메일_중복_확인() {
        // 예외발생이 잘 되는지 확인.
        Assertions.assertThrows(MemberException.class, () -> userServiceV2.validateEmail("teamwemmy@gmail.com"));
    }
}