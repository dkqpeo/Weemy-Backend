package wemmy.api.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import wemmy.dto.benefit.BenefitDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BenefitListControllerTest {

    @Autowired
    private BenefitListController benefitListController;

    @Test
    void getBenefitTitleListWeb() {

    }
}