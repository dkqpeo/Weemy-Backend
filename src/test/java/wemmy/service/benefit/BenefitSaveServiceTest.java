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
        List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave();

        benefitService.benefitParseAndSave(benefitSaveDTO);
    }

}