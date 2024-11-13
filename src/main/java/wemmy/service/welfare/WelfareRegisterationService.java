package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.dto.welfare.WelfareRegisterListRespDTO;
import wemmy.dto.welfare.program.ProgramRegisterDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WelfareRegisterationService {

    private final ProgramRegistrationService programRegistrationService;
    private final BenefitRegistrationService benefitRegistrationService;

    public void programRegister(ProgramRegisterDTO dto, UserEntityV2 user, Program program) {

        ProgramRegistration register = ProgramRegistration.builder()
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .birthday(dto.getBirthday())
                .program(program)
                .user(user)
                .phone(dto.getPhone())
                .name(dto.getName())
                .email(dto.getEmail())
                .createTime(LocalDateTime.now())
                .build();

        programRegistrationService.register(register);
    }

    public void welfareRegister(ProgramRegisterDTO dto, UserEntityV2 user, Welfare welfare) {

        WelfareRegistration register = WelfareRegistration.builder()
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .birthday(dto.getBirthday())
                .welfare(welfare)
                .user(user)
                .phone(dto.getPhone())
                .name(dto.getName())
                .email(dto.getEmail())
                .createTime(LocalDateTime.now())
                .build();

        benefitRegistrationService.register(register);
    }

    public List<WelfareRegisterListRespDTO.response> registerList() {

        List<ProgramRegistration> programList = programRegistrationService.findAll();
        List<WelfareRegistration> benefitList = benefitRegistrationService.findAll();

        List<WelfareRegisterListRespDTO.welfareRegister> programRegisterList = new ArrayList<>();
        List<WelfareRegisterListRespDTO.welfareRegister> benefitRegisterList = new ArrayList<>();

        for (ProgramRegistration program : programList) {
            WelfareRegisterListRespDTO.welfareRegister data = WelfareRegisterListRespDTO.welfareRegister.builder()
                    .registerId(program.getId())
                    .registerDate(program.getCreateTime())
                    .title(program.getProgram().getTitle())
                    .name(program.getName())
                    .phone(program.getPhone())
                    .email(program.getEmail())
                    .address(program.getAddress())
                    .addressDetail(program.getAddressDetail())
                    .build();

            programRegisterList.add(data);
        }

        for (WelfareRegistration benefit : benefitList) {
            WelfareRegisterListRespDTO.welfareRegister data = WelfareRegisterListRespDTO.welfareRegister.builder()
                    .registerId(benefit.getId())
                    .registerDate(benefit.getCreateTime())
                    .title(benefit.getWelfare().getTitle())
                    .name(benefit.getName())
                    .phone(benefit.getPhone())
                    .email(benefit.getEmail())
                    .address(benefit.getAddress())
                    .addressDetail(benefit.getAddressDetail())
                    .build();

            benefitRegisterList.add(data);
        }

        WelfareRegisterListRespDTO.response program = WelfareRegisterListRespDTO.response.builder()
                .group("program")
                .data(programRegisterList)
                .build();

        WelfareRegisterListRespDTO.response benefit = WelfareRegisterListRespDTO.response.builder()
                .group("benefit")
                .data(benefitRegisterList)
                .build();

        List<WelfareRegisterListRespDTO.response> response = new ArrayList<>();

        response.add(program);
        response.add(benefit);
        return response;
    }

    public void validateProgram(UserEntityV2 user, Program program) {

        Optional<ProgramRegistration> result = programRegistrationService.validateProgram(program, user);

        if(result.isPresent())
            throw new ControllerException(ErrorCode.ALREADY_REGISTERED_PROGRAM);
    }

    public void validateBenefit(UserEntityV2 user, Welfare welfare) {

        Optional<WelfareRegistration> result = benefitRegistrationService.validateBenefit(welfare, user);

        if(result.isPresent())
            throw new ControllerException(ErrorCode.ALREADY_REGISTERED_PROGRAM);
    }
}
