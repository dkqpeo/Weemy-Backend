package wemmy.api.benefit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.service.benefit.BenefitService;


@Tag(name = "Benefit", description = "복지 정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit")
public class BenefitDetailController {

    private final BenefitService benefitService;

    /**
     * 복지 내용 상세조회
     */
    @Tag(name = "Benefit")
    @Operation(summary = "앱 복지 상세조회 API", description = "accessToken필요, benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/detail/{id}")
    public ResponseEntity<BenefitDTO.response> getBenefitDetail(@PathVariable("id") Long id) {

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getBenefitDetail(id);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }

    /**
     * 웹 복지 내용 상세조회
     */
    @Tag(name = "Benefit")
    @Operation(summary = "웹 복지 상세조회 API", description = "benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/web/detail")
    public ResponseEntity<BenefitDTO.response> getBenefitDetailWeb(@RequestParam("id") Long id,
                                                                   HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getBenefitDetail(id);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }
}
