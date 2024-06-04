package wemmy.service.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.dto.benefit.BenefitSaveDTO;

import java.util.List;

@SpringBootTest
class BenefitSaveServiceTest {

    @Autowired
    private BenefitSaveService benefitSaveService;

    @Autowired BenefitService benefitService;

    @Test
    void benefitSave() {
        // resource/benefit에 있는 json파일 파싱
        List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave();

        // 파싱한 결과를 데이터베이스에 저장
        benefitService.benefitParseAndSave(benefitSaveDTO);
    }

}