package wemmy.api.scrap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Welfare;
import wemmy.service.area.AreaService;
import wemmy.service.benefit.BenefitServiceV2;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.WelfareService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScrapAddControllerV2Test {

    @Autowired
    private UserServiceV2 userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private WelfareService welfareService;

    @Autowired
    private ScrapServiceV2 scrapService;

/*    @Test
    void scrapSave() {
        Long userID = 1L;

        UserEntityV2 user = userService.findByUserId(userID);

        Welfare welfare = welfareService.findById(79L);
        scrapService.scrapSaveByBenefit(user, welfare);

    }*/
}
