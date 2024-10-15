package wemmy.api.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.service.area.AreaService;
import wemmy.service.benefit.BenefitServiceV2;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserServiceV2;

import java.util.List;

@SpringBootTest
class BenefitListControllerV2Test {

    @Autowired
    private UserServiceV2 userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private BenefitServiceV2 benefitService;

    @Autowired
    private ScrapServiceV2 scrapService;


    /*@Test
    void getBenefitTitleList() {

        Long userID = 1L;

        UserEntityV2 user = userService.findByUserId(userID);

        String city = user.getSigg_id().getSido_id().getName();
        String district = user.getSigg_id().getName();


        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // 회원이 스크랩 한 복지정보 리스트
        List<ScrapDTO.response> scrapList = scrapService.scrapList(user);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponse> benefitList = benefitService.getBenefitTitleList(region, government, city, district, user, scrapList);

        for (BenefitDTO.titleResponse titleResponse : benefitList) {
            System.out.println(titleResponse.getTitle());
            System.out.println(titleResponse.getGroup());
        }
    }*/
}