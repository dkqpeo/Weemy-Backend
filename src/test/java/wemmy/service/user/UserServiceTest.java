package wemmy.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.user.UserEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void signUpByAdmin() {

        // 관리자 계정 생성
        userService.signUpByAdmin("teamwemmy@gmail.com", "project2024");
    }

    @Test
    void getUserInfo() {
        UserEntity user = userService.findByUserId(2L);

        System.out.println(user.getSigg_id().getSido_id().getAdm_code());
    }
}