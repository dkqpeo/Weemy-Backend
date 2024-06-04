package wemmy.service.benefit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.baby.constant.BabyType;
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

import static wemmy.domain.baby.constant.BabyType.PARENTING;
import static wemmy.domain.baby.constant.BabyType.PREGNANCY;

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
            Long uniqueId = benefit.getUnique_id();
            String admin = benefit.getAdmin_id();
            Long wCategoryId = (long) benefit.getW_category_id();
            String hostId = benefit.getHost_id();
            String title = benefit.getTitle();
            String field = benefit.getField();
            String content = benefit.getContent();
            String way = benefit.getWay();
            String etc = benefit.getEtc();
            String originalUrl = benefit.getOriginal_url();

            UserEntity adminId = userService.valudateAdmin(admin);      // 관리자 계정
            Wcategory wcategory = welfareService.getWcategoryByWcategoryId(wCategoryId);        // 복지 카테고리
            Regions region = areaService.validateRegionCode(hostId);    // 지역코드

            String imageUrl = "";
            if(region.getSigg_id().getName().equals("금천구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/geumcheon-gu.png?raw=true";
            } else if(region.getSigg_id().getName().equals("정부")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/government.png?raw=true";
            }

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
                    .imageUrl(imageUrl)
                    .build();

            // 파싱이 완료되면 저장.
            welfareService.welfareSave(welfare);

            System.out.println("저장 완료");
        }
    }

    /**
     * 앱 홈화면에 보여질 혜택 제목리스트
     */
    public List<BenefitDTO.titleResponse> getBenefitTitleList(Regions regions, Regions government, String city, String district, BabyType babyType) {

        // 자치구 혜택.
        List<Welfare> welfareList = welfareService.findAllByWelfareById(regions);
        // 정부 혜택.
        List<Welfare> governmentList = welfareService.findAllByWelfareById(government);
        // Response List.
        List<BenefitDTO.titleResponse> benefitList;

        Long categoryId = null;

        // 임신/육아 여부 확인.
        if(babyType == PREGNANCY) {
            categoryId = 1L;
        } else if (babyType == PARENTING) {
            categoryId = 2L;
        }

        benefitList = parseWelfareTitle(welfareList, city, district, categoryId);
        benefitList.addAll(parseWelfareTitle(governmentList, "정부", "", categoryId));

        return benefitList;
    }

    /**
     * benefit_id로 상세 복지정보 조회.
     * @return BenefitDTO.response
     */
    public BenefitDTO.response getBenefitDetail(Long benefit_id) {
        Welfare detailBenefit = welfareService.findById(benefit_id);

        String city = detailBenefit.getHostId().getSido_id().getName();
        String district = detailBenefit.getHostId().getSigg_id().getName();

        BenefitDTO.response build = BenefitDTO.response.builder()
                .benefitId(detailBenefit.getId())
                .wCategoryId(detailBenefit.getWCategoryId().getId())
                .title(detailBenefit.getTitle())
                .field(detailBenefit.getField())
                .content(detailBenefit.getContent())
                .way(detailBenefit.getWay())
                .etc(detailBenefit.getEtc())
                .originalUrl(detailBenefit.getOriginalUrl())
                .city(city)
                .district(district)
                .imageUrl(detailBenefit.getImageUrl())
                .build();

        return build;
    }

    private List<BenefitDTO.response> parseWelfare(List<Welfare> list, String city, String district) {
        List<BenefitDTO.response> resultList = new ArrayList<>();

        for (Welfare welfare : list) {
            BenefitDTO.response dto = BenefitDTO.response.builder()
                    .benefitId(welfare.getId())
                    //.unique_id(welfare.getUniqueId())
                    //.admin_id(welfare.getAdminId().getId())
                    .wCategoryId(welfare.getWCategoryId().getId())
                    //.host_id(welfare.getHostId().getRegion_cd())
                    .title(welfare.getTitle())
                    .field(welfare.getField())
                    .content(welfare.getContent())
                    .way(welfare.getWay())
                    .etc(welfare.getEtc())
                    .originalUrl(welfare.getOriginalUrl())
                    .city(city)
                    .district(district)
                    .build();

            resultList.add(dto);
        }
        return resultList;
    }

    private List<BenefitDTO.titleResponse> parseWelfareTitle(List<Welfare> list, String city, String district, Long categoryId) {
        List<BenefitDTO.titleResponse> resultList = new ArrayList<>();

        for (Welfare welfare : list) {
            if(welfare.getWCategoryId().getId().equals(categoryId)){
                BenefitDTO.titleResponse dto = BenefitDTO.titleResponse.builder()
                        .benefitId(welfare.getId())
                        .title(welfare.getTitle())
                        .city(city)
                        .district(district)
                        .imageUrl(welfare.getImageUrl())
                        .build();

                resultList.add(dto);
            }
        }
        return resultList;
    }
}
