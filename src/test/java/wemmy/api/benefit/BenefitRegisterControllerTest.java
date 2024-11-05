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

        UserEntityV2 user = userServiceV2.findByUserId(2L);
        // 신청할 프로그램.
        Program program = programService.findById(22L);

        ProgramRegistration register = ProgramRegistration.builder()
                .address("dto.getAddress()")
                .addressDetail("dto.getAddressDetail()")
                .birthday(LocalDate.parse("1999-08-02"))
                .program(program)
                .user(user)
                .build();

        welfareRegisterationService.programRegister(register);
    }
}