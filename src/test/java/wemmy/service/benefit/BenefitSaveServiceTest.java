package wemmy.service.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.dto.benefit.BenefitSaveDTO;
import wemmy.service.area.AreaService;

import java.util.List;

@SpringBootTest
class BenefitSaveServiceTest {

    @Autowired
    private BenefitSaveService benefitSaveService;

    @Autowired
    private BenefitService benefitService;

    @Autowired
    private AreaService areaService;

    @Test
    void benefitSave() {
        // resource/benefit에 있는 json파일 파싱
        try {
            List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave();

            // 파싱한 결과를 데이터베이스에 저장
            benefitService.benefitParseAndSave(benefitSaveDTO);
        } catch (Exception e) {

        }
    }

    @Test
    void benefitTitleListWeb() {
        Regions government = areaService.getRegionById(494L);
        benefitService.getBenefitTitleListWeb(government, "서울특별시", "");
    }

}