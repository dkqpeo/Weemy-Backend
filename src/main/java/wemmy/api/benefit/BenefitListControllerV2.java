package wemmy.api.benefit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.area.AreaService;
import wemmy.service.benefit.BenefitServiceV2;
import wemmy.service.scrap.ScrapServiceV2;
import wemmy.service.user.UserServiceV2;

import java.util.List;

@Tag(name = "BenefitV2", description = "복지, 프로그램 정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit/v2")
public class BenefitListControllerV2 {


    private final UserServiceV2 userServiceV2;
    private final BenefitServiceV2 benefitService;
    private final AreaService areaService;
    private final ScrapServiceV2 scrapService;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * APP 요청 처리
     * 사용자의 거주지, 사용자 토픽에 맞는 복지 리스트 조회
     * 토픽 수집 중단으로 인해 미사용.
     */
    /*@Tag(name = "BenefitV2")
    @Operation(summary = "APP 홈화면 복지리스트 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답.")
    @GetMapping("/list/home")*/
    public ResponseEntity<List<BenefitDTO.titleResponse>> getBenefitTitleList(HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        String city = user.getSigg_id().getSido_id().getName();
        String district = user.getSigg_id().getName();


        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // 회원이 스크랩 한 복지정보 리스트
        List<ScrapDTO.response> scrapList = scrapService.scrapList(user);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponse> benefitList = benefitService.getBenefitTitleList(region, government, city, district, user, scrapList);
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    /**
     * APP 요청 처리
     * 사용자의 거주지, 요청한 카테고리에 맞는 복지, 프로그램 조회
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "APP 홈 분야별 혜택리스트 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답.")
    @GetMapping("/list/home/{group}")
    public ResponseEntity<List<BenefitDTO.titleResponse>> getBenefitTitleListByGroup(@PathVariable("group") String group,
                                                                                     HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userID);

        String city = user.getSigg_id().getSido_id().getName();
        String district = user.getSigg_id().getName();


        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // 회원이 스크랩 한 복지정보 리스트
        List<ScrapDTO.response> scrapList = scrapService.scrapList(user);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponse> benefitList = benefitService.getBenefitTitleListByGroup(region, government, city, district, user, scrapList, group);
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    /**
     * WEB 요청 처리. 입력받은 시의 모든 복지정보를 제공.
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "WEB 복지리스트 API", description = "요청쿼리로 보낸 지역시에 해당하는 복지정보 응답.")
    @GetMapping("/web/list")
    public ResponseEntity<List<BenefitDTO.titleResponseWeb>> getBenefitTitleListByCityWeb(@RequestParam("city") String reqCity,
                                                                                          HttpServletRequest httpServletRequest) {
        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponseWeb> benefitList = benefitService.getBenefitTitleListWeb(government, reqCity, "");
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    /**
     * WEB 요청 처리. 입력받은 시, 구의 모든 복지정보를 제공.
     */
    @Tag(name = "BenefitV2")
    @Operation(summary = "WEB 복지리스트 API", description = "요청쿼리로 보낸 지역시, 구에 해당하는 복지정보 응답.")
    @GetMapping("/web/list/district")
    public ResponseEntity<List<BenefitDTO.titleResponseWeb>> getBenefitTitleListByCityAndDistrictWeb(@RequestParam("city") String reqCity,
                                                                                                     @RequestParam("district") String reqDistrict) {

        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponseWeb> benefitList = benefitService.getBenefitTitleListWeb(government, reqCity, reqDistrict);
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }
}
