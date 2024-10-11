package wemmy.api.scrap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.ResponseDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.scrap.ScrapService;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserService;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareService;

@Tag(name = "ScrapV2", description = "복지 정보 스크랩 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/scrap/v2")
public class ScrapAddControllerV2 {

    private final ScrapServiceV2 scrapService;
    private final WelfareService welfareService;
    private final ProgramService programService;
    private final UserServiceV2 userService;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "ScrapV2")
    @Operation(summary = "복지정보 스크랩 저장 API", description = "사용자가 저장을 원하는 복지정보 스크랩 API")
    @GetMapping("/save/{benefitId}/{group}")
    public ResponseEntity<ResponseDTO> scrapSave(@PathVariable("benefitId") Long reqBenefitId,
                                                 @PathVariable("group") String group,
                                                 HttpServletRequest httpServletRequest) {

        log.info("복지정보 스크랩 저장.");
        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 토큰에서 회원정보 가져오기.
        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userService.findByUserId(userId);

        // 저장할 복지정보
        if(group.equals("benefit")) {
            Welfare welfare = welfareService.findById(reqBenefitId);
            scrapService.scrapSaveByBenefit(user, welfare);
        } else if (group.equals("program")) {
            Program program = programService.findById(reqBenefitId);
            scrapService.scrapSaveByProgram(user, program);
        }

        return new ResponseEntity<>(ResponseDTO.of("스크랩 완료.") , HttpStatus.CREATED);
    }

}
