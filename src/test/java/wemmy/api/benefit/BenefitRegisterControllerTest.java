package wemmy.api.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.ProgramRegistrationService;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareRegisterationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BenefitRegisterControllerTest {

    @Autowired
    private UserServiceV2 userServiceV2;

    @Autowired
    private ProgramService programService;

    @Autowired
    private WelfareRegisterationService welfareRegisterationService;

    @Test
    void programRegister() {
    }
}