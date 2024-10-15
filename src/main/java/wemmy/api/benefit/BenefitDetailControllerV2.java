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
import wemmy.dto.benefit.BenefitDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
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
    private final GetUserIDByToken getUserIDByToken;

    /**
     * App
     * 복지 내용 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 복지 상세조회 API", description = "accessToken필요, benefitId에 해당하는 상세 복지정보 응답., group = benefit/program")
    @GetMapping("/detail/{id}/{group}")
    public ResponseEntity<?> getBenefitDetail(@PathVariable("id") Long id,
                                              @PathVariable("group") String group,
                                              HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        // 사용자 정보와 복지 정보 기본키로 스크랩 여부 조회.
        String scrap = scrapServiceV2.findScrap(user, id, group);

        System.out.println(scrap);

        // region code로 복지(혜택)정보 조회.
        if (group.equals("benefit")) {
            BenefitDTO.benefitResponse benefitDetail = benefitServiceV2.getBenefitDetail(id, scrap, group);
            return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
        } else if (group.equals("program")) {
            BenefitDTO.programResponse programDetail = benefitServiceV2.getProgramDetail(id, scrap, group);
            return new ResponseEntity<>(programDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 웹 복지 내용 상세조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "WEB 복지 상세조회 API", description = "benefitId에 해당하는 상세 복지정보 응답., group = benefit/program")
    @GetMapping("/web/detail")
    public ResponseEntity<?> getBenefitDetailWeb(@RequestParam("id") Long id,
                                                 @RequestParam("group") String group,
                                                 HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // region code로 복지(혜택)정보 조회.
        if (group.equals("benefit")) {
            BenefitDTO.benefitResponse benefitDetail = benefitServiceV2.getBenefitDetail(id, null, group);
            return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
        } else if (group.equals("program")) {
            BenefitDTO.programResponse programDetail = benefitServiceV2.getProgramDetail(id, "", group);
            return new ResponseEntity<>(programDetail, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
