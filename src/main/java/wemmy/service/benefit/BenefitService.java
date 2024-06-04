package wemmy.service.benefit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Wcategory;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.dto.benefit.BenefitSaveDTO;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;
import wemmy.service.welfare.WelfareService;

import java.util.ArrayList;
import java.util.List;

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

    // 자치구 별 혜택정보 조회.
    public List<BenefitDTO.response> getBenefitList(Regions regions, Regions government, String city, String district) {

        // 자치구 혜택.
        List<Welfare> welfareList = welfareService.findAllByWelfareById(regions);
        // 정부 혜택.
        List<Welfare> governmentList = welfareService.findAllByWelfareById(government);
        // Response List.
        List<BenefitDTO.response> benefitList;

        benefitList = parseWelfare(welfareList, city, district);
        benefitList.addAll(parseWelfare(governmentList, "정부", ""));

        return benefitList;
    }

    private List<BenefitDTO.response> parseWelfare(List<Welfare> list, String city, String district) {
        List<BenefitDTO.response> resultList = new ArrayList<>();

        for (Welfare welfare : list) {
            BenefitDTO.response dto = BenefitDTO.response.builder()
                    .benefit_id(welfare.getId())
                    .unique_id(welfare.getUniqueId())
                    .admin_id(String.valueOf(welfare.getAdminId().getId()))
                    .w_category_id(welfare.getWCategoryId().getId())
                    .host_id(welfare.getHostId().getRegion_cd())
                    .title(welfare.getTitle())
                    .field(welfare.getField())
                    .content(welfare.getContent())
                    .way(welfare.getWay())
                    .etc(welfare.getEtc())
                    .original_url(welfare.getOriginalUrl())
                    .city(city)
                    .district(district)
                    .build();

            resultList.add(dto);
        }
        return resultList;
    }
}
