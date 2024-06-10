package wemmy.api.benefit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.area.Regions;
import wemmy.domain.area.district.UmdAreas;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntity;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.area.AreaService;
import wemmy.service.baby.BabyService;
import wemmy.service.benefit.BenefitService;
import wemmy.service.user.UserService;

import java.util.List;

@Tag(name = "Benefit", description = "복지 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit")
public class BenefitListController {


    private final UserService userService;
    private final BabyService babyService;
    private final BenefitService benefitService;
    private final AreaService areaService;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * 사용자의 거주지, 임신/육아 여부에 맞는 복지 리스트 조회
     */
    @Tag(name = "Benefit")
    @Operation(summary = "앱 홈화면 복지리스트 API", description = "accessToken에 있는 사용자 정보에 해당하는 복지정보 응답.")
    @GetMapping("/list/home")
    public ResponseEntity<List<BenefitDTO.titleResponse>> getBenefitTitleList(HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntity user = userService.findByUserId(userID);
        BabyEntity babyInfo = babyService.findBabyByUserId(user.getId());
        
        String city = user.getSigg_id().getSido_id().getName();
        String district = user.getSigg_id().getName();
        BabyType babyType = babyInfo.getType();

        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponse> benefitList = benefitService.getBenefitTitleList(region, government, city, district, babyType);
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    /**
     * 웹 요청 처리. 입력받은 시의 모든 복지정보를 제공.
     */
    @Tag(name = "Benefit")
    @Operation(summary = "앱 복지리스트 API", description = "요청쿼리로 보낸 지역시에 해당하는 복지정보 응답.")
    @GetMapping("/web/list")
    public ResponseEntity<List<BenefitDTO.titleResponseWeb>> getBenefitTitleListByCityWeb(@RequestParam("city") String reqCity) {

        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponseWeb> benefitList = benefitService.getBenefitTitleListWeb(government, reqCity, null);
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    /**
     * 웹 요청 처리. 입력받은 시, 구의 모든 복지정보를 제공.
     */
    @Tag(name = "Benefit")
    @Operation(summary = "앱 복지리스트 API", description = "요청쿼리로 보낸 지역시, 구에 해당하는 복지정보 응답.")
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
