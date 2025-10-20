package wemmy.api.benefit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.ResponseDTO;
import wemmy.dto.welfare.WelfareRegisterListRespDTO;
import wemmy.dto.welfare.program.ProgramRegisterDTO;
import wemmy.global.security.CustomUserDetails;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareRegisterationService;
import wemmy.service.welfare.WelfareService;

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

    /**
     * APP 프로그램 신청
     * @return String 신청 완료, 실패.
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 프로그램 신청 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답. \n 요청 바디에 address, addressDetail, birthday필요.")
    @PostMapping("/{group}/{programId}")
    public ResponseEntity<ResponseDTO> programRegister(@PathVariable("group") String group,
                                             @PathVariable("programId") Long programId,
                                             @RequestBody ProgramRegisterDTO dto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 인증된 사용자 정보 가져오기. (이름, 연락처, 이메일)
        Long userID = userDetails.getUserId();
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        // 신청할 프로그램 구분.
        if(group.equals("program")) {
            Program program = programService.findById(programId);

            welfareRegisterationService.validateProgram(user, program);
            welfareRegisterationService.programRegister(dto, user, program);

            return new ResponseEntity<>(ResponseDTO.of("프그로램 신청 완료.") , HttpStatus.OK);
        }

        else if(group.equals("benefit")) {
            Welfare welfare = welfareService.findById(programId);

            welfareRegisterationService.validateBenefit(user, welfare);
            welfareRegisterationService.welfareRegister(dto, user, welfare);

            return new ResponseEntity<>(ResponseDTO.of("복지 신청 완료.") , HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDTO.of("서버 통신 중 문제가 발생하였습니다. 관리자에게 문의하여 주세요.") , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 프로그램 신청 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답. \n 요청 바디에 address, addressDetail, birthday필요.")
    @GetMapping("/list")
    public ResponseEntity<List<WelfareRegisterListRespDTO.response>> registerList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                   HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        Long userID = userDetails.getUserId();
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        userServiceV2.validateAdmin(user.getEmail());

        List<WelfareRegisterListRespDTO.response> response = welfareRegisterationService.registerList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
