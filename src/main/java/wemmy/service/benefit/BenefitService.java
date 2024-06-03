package wemmy.service.benefit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Wcategory;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.benefit.BenefitSaveDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.welfare.WcategoryRepository;
import wemmy.repository.welfare.WelfareRepository;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;
import wemmy.service.welfare.WelfareService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BenefitService {

    private final WelfareService welfareService;
    private final UserService userService;
    private final AreaService areaService;

    // 크롤링한 혜택정보 저장.
    public void benefitParseAndSave(List<BenefitSaveDTO> benefitList) {
        for (BenefitSaveDTO benefit : benefitList) {
            int uniqueId = benefit.getUnique_id();
            String admin = benefit.getAdmin_id();
            Long wCategoryId = (long) benefit.getW_category_id();
            String hostId = benefit.getHost_id();
            String title = benefit.getTitle();
            String field = benefit.getField();
            String content = benefit.getContent();
            String way = benefit.getWay();
            String inquiry = benefit.getInquiry();
            String etc = benefit.getEtc();
            String originalUrl = benefit.getOriginal_url();

            UserEntity adminId = userService.valudateAdmin(admin);
            Wcategory wcategory = welfareService.getWcategoryByWcategoryId(wCategoryId);
            Regions region = areaService.validateRegionCode(hostId);

            Welfare welfare = Welfare.builder()
                    .uniqueId(uniqueId)
                    .title(title)
                    .field(field)
                    .content(content)
                    .way(way)
                    .inquiry(inquiry)
                    .etc(etc)
                    .originalUrl(originalUrl)
                    .adminId(adminId)
                    .wCategoryId(wcategory)
                    .hostId(region)
                    .build();

            // 파싱이 완료되면 저장.
            welfareService.welfareSave(welfare);

            System.out.println("저장 완료");
        }
    }
}
