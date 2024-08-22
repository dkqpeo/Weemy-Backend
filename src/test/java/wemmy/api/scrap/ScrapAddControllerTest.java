package wemmy.api.scrap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;
import wemmy.service.scrap.ScrapService;
import wemmy.service.user.UserService;
import wemmy.service.welfare.WelfareService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScrapAddControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private WelfareService welfareService;
    @Test
    void scrapSave() {

        UserEntity user = userService.findByUserId(2L);
        //Optional<UserEntity> user = userService.finBydUserEmail("lan0184@naver.com");

        Welfare welfare = welfareService.findById(98L);

        scrapService.scrapSave(user, welfare);
    }
}