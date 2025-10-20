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
import wemmy.dto.welfare.benefit.BenefitDTO;
import wemmy.global.security.CustomUserDetails;
import wemmy.service.benefit.BenefitServiceV2;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserServiceV2;

@Tag(name = "BenefitV2", description = "복지 정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit/v2")
public class BenefitDetailControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final BenefitServiceV2 benefitServiceV2;
    private final ScrapServiceV2 scrapServiceV2;

    /**
     * App
     * 복지(Benefit) 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 복지 상세조회 API", description = "accessToken필요, benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/detail/benefit/{id}")
    public ResponseEntity<BenefitDTO.benefitResponse> getBenefitDetail(@PathVariable("id") Long id,
                                                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                        HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 인증된 사용자 정보로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = userDetails.getUserId();
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        // 사용자 정보와 복지 정보 기본키로 스크랩 여부 조회.
        String scrap = scrapServiceV2.findScrap(user, id, "benefit");

        log.debug("scrap: {}", scrap);

        // 복지(혜택)정보 조회
        BenefitDTO.benefitResponse benefitDetail = benefitServiceV2.getBenefitDetail(id, scrap, "benefit");
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }

    /**
     * App
     * 프로그램(Program) 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 프로그램 상세조회 API", description = "accessToken필요, programId에 해당하는 상세 프로그램정보 응답.")
    @GetMapping("/detail/program/{id}")
    public ResponseEntity<BenefitDTO.programResponse> getProgramDetail(@PathVariable("id") Long id,
                                                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                        HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 인증된 사용자 정보로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = userDetails.getUserId();
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        // 사용자 정보와 프로그램 정보 기본키로 스크랩 여부 조회.
        String scrap = scrapServiceV2.findScrap(user, id, "program");

        log.debug("scrap: {}", scrap);

        // 프로그램정보 조회
        BenefitDTO.programResponse programDetail = benefitServiceV2.getProgramDetail(id, scrap, "program");
        return new ResponseEntity<>(programDetail, HttpStatus.OK);
    }

    /**
     * 웹 복지(Benefit) 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "WEB 복지 상세조회 API", description = "benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/web/detail/benefit")
    public ResponseEntity<BenefitDTO.benefitResponse> getBenefitDetailWeb(@RequestParam("id") Long id,
                                                                           HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 복지(혜택)정보 조회
        BenefitDTO.benefitResponse benefitDetail = benefitServiceV2.getBenefitDetail(id, null, "benefit");
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }

    /**
     * 웹 프로그램(Program) 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "WEB 프로그램 상세조회 API", description = "programId에 해당하는 상세 프로그램정보 응답.")
    @GetMapping("/web/detail/program")
    public ResponseEntity<BenefitDTO.programResponse> getProgramDetailWeb(@RequestParam("id") Long id,
                                                                           HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 프로그램정보 조회
        BenefitDTO.programResponse programDetail = benefitServiceV2.getProgramDetail(id, "", "program");
        return new ResponseEntity<>(programDetail, HttpStatus.OK);
    }
}
