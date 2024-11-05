package wemmy.api.benefit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.dto.ResponseDTO;
import wemmy.dto.welfare.WelfareRegisterListRespDTO;
import wemmy.dto.welfare.program.ProgramRegisterDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.*;

import java.util.List;

@Tag(name = "BenefitV2", description = "복지, 프로그램 정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit/v2/register")
public class BenefitRegisterController {

    private final UserServiceV2 userServiceV2;
    private final ProgramService programService;
    private final WelfareService welfareService;
    private final WelfareRegisterationService welfareRegisterationService;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * APP 프로그램 신청
     * @return String 신청 완료, 실패.
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 프로그램 신청 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답.")
    @GetMapping("/{group}/{programId}")
    public ResponseEntity<?> programRegister(@PathVariable("group") String group,
                                             @PathVariable("programId") Long programId,
                                             @RequestBody ProgramRegisterDTO dto,
                                             HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 사용자 정보 가져오기. (이름, 연락처, 이메일)
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        // 신청할 프로그램.
        if(group.equals("program")) {
            Program program = programService.findById(programId);
            ProgramRegistration register = ProgramRegistration.builder()
                    .address(dto.getAddress())
                    .addressDetail(dto.getAddressDetail())
                    .birthday(dto.getBirthday())
                    .program(program)
                    .user(user)
                    .build();

            welfareRegisterationService.programRegister(register);

            return new ResponseEntity<>(ResponseDTO.of("프그로램 신청 완료.") , HttpStatus.OK);
        }

        // 신청할 프로그램.
        else if(group.equals("benefit")) {
            Welfare welfare = welfareService.findById(programId);
            WelfareRegistration register = WelfareRegistration.builder()
                    .address(dto.getAddress())
                    .addressDetail(dto.getAddressDetail())
                    .birthday(dto.getBirthday())
                    .welfare(welfare)
                    .user(user)
                    .build();

            welfareRegisterationService.welfareRegister(register);

            return new ResponseEntity<>(ResponseDTO.of("복지 신청 완료.") , HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDTO.of("서버 통신 중 문제가 발생하였습니다. 관리자에게 문의하여 주세요.") , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/list")
    public ResponseEntity<?> registerList() {
        List<WelfareRegisterListRespDTO.response> response = welfareRegisterationService.registerList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
