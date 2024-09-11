package wemmy.api.scrap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.ResponseDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.scrap.ScrapService;
import wemmy.service.user.UserService;
import wemmy.service.welfare.WelfareService;

@Tag(name = "Scrap", description = "복지 정보 스크랩 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/scrap")
public class ScrapAddController {

    private final ScrapService scrapService;
    private final WelfareService welfareService;
    private final UserService userService;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "Scrap")
    @Operation(summary = "복지정보 스크랩 저장 API", description = "사용자가 저장을 원하는 복지정보 스크랩 API")
    @GetMapping("/save/{benefitId}")
    public ResponseEntity<ResponseDTO> scrapSave(@PathVariable("benefitId") Long reqBenefitId,
                                                 HttpServletRequest httpServletRequest) {

        log.info("복지정보 스크랩 저장.");
        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 토큰에서 회원정보 가져오기.
        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        UserEntity user = userService.findByUserId(userId);

        // 저장할 복지정보 
        Welfare welfare = welfareService.findById(reqBenefitId);

        scrapService.scrapSave(user, welfare);

        return new ResponseEntity<>(ResponseDTO.of("스크랩 완료.") , HttpStatus.CREATED);
    }

}
