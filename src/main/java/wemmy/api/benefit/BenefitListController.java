package wemmy.api.benefit;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.domain.area.Regions;
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
     * 복지 내용 상세조회
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<BenefitDTO.response> getBenefitDetail(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getBenefitDetail(id);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }
}
