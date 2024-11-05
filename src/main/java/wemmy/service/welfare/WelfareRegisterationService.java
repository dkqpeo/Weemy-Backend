package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.dto.welfare.WelfareRegisterListRespDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WelfareRegisterationService {

    private final ProgramRegistrationService programRegistrationService;
    private final BenefitRegistrationService benefitRegistrationService;

    public void programRegister(ProgramRegistration register) {
        programRegistrationService.register(register);
    }

    public void welfareRegister(WelfareRegistration register) {
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
                    .name(program.getUser().getName())
                    .phone(program.getUser().getPhone())
                    .email(program.getUser().getEmail())
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
                    .name(benefit.getUser().getName())
                    .phone(benefit.getUser().getPhone())
                    .email(benefit.getUser().getEmail())
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
}
