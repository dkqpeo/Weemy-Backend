package wemmy.service.benefit;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.welfare.benefit.BenefitDTO;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.service.area.AreaService;
import wemmy.service.baby.BabyService;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserServiceV2;

import java.util.List;

import static wemmy.domain.baby.constant.BabyType.PREGNANCY;

@SpringBootTest
class BenefitSaveServiceTest {

    @Autowired
    private BenefitSaveService benefitSaveService;

    @Autowired
    private BenefitServiceV2 benefitService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserServiceV2 userService;

    @Autowired
    private BabyService babyService;

    @Autowired
    private ScrapServiceV2 scrapService;

    @Test
    void benefitSave() {
        // resource/benefit에 있는 json파일 파싱
        try {
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/dj_2024-07-10_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ga_2024-07-12_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/geumcheon_2024-07-11_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gj_2024-07-09_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gn_2024-07-15_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gr_2024-07-12_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gs_2024-07-12_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/sh_2024-07-15_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ydp_2024-07-08_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ydp_2024-07-09_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ydp_2024-07-10_result.json");

            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/db_2024-08-12_result (1).json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ep_2024-08-06_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gb_2024-08-12_result (1).json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gd_2024-08-05_result (2).json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/gn_2024-08-05_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/mp_2024-08-05_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/nw_2024-08-12_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/sb_2024-08-12_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/sdm_2024-08-06_result (2).json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/sp_2024-08-05_result (3).json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/yc_2024-08-05_result (2).json");

            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ddm_2024-08-29_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/jg_2024-08-30_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/jn_2024-08-28_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/jr_2024-08-30_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/sd_2024-08-29_result.json");
            //List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/ys_2024-08-29_result.json");

            /*List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave("/benefit/dj_2024-07-10_result.json");
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ga_2024-07-12_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/geumcheon_2024-07-11_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gj_2024-07-09_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gr_2024-07-12_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gs_2024-07-12_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/sh_2024-07-15_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ydp_2024-07-08_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ydp_2024-07-09_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ydp_2024-07-10_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/db_2024-08-12_result (1).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ep_2024-08-06_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gb_2024-08-12_result (1).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gd_2024-08-05_result (2).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/gn_2024-08-05_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/mp_2024-08-05_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/nw_2024-08-12_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/sb_2024-08-12_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/sdm_2024-08-06_result (2).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/sp_2024-08-05_result (3).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/yc_2024-08-05_result (2).json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ddm_2024-08-29_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/jg_2024-08-30_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/jn_2024-08-28_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/jr_2024-08-30_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/sd_2024-08-29_result.json"));
            benefitSaveDTO.addAll(benefitSaveService.benefitSave("/benefit/ys_2024-08-29_result.json"));

            // 파싱한 결과를 데이터베이스에 저장
            benefitService.benefitParseAndSave(benefitSaveDTO);*/

        } catch (Exception e) {

        }
    }

    /**
     * 앱 홈화면 복지정보 테스트
     */
    @Test
    void benefitTitleListApp() {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        UserEntityV2 user = userService.findByUserId(2L);
        BabyEntity babyInfo = babyService.findBabyByUserId(user.getId());

        String city = "서울특별시";
        String district = "금천구";
        BabyType babyType = PREGNANCY;

        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // 회원이 스크랩 한 복지정보 리스트
        List<ScrapDTO.response> scrapList = scrapService.scrapList(user);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.benefitTitleResponse> benefitList = benefitService.getBenefitTitleList(region, government, city, district, babyType, scrapList);

        for (BenefitDTO.benefitTitleResponse titleResponse : benefitList) {
            System.out.println(titleResponse.getBenefitId());
            System.out.println(titleResponse.getTitle());
            System.out.println(titleResponse.getCity());
            System.out.println(titleResponse.getDistrict());
            System.out.println("scrap = false");
        }
    }


    /**
     * 웹 복지 리스트 테스트
     */
    @Test
    void benefitTitleListWeb() {
        Regions government = areaService.getRegionById(494L);
        benefitService.getBenefitTitleListWeb(government, "서울특별시", "");
    }

}