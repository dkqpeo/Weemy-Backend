package wemmy.service.benefit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Wcategory;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.dto.welfare.benefit.BenefitDTO;
import wemmy.dto.welfare.benefit.BenefitSaveDTO;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BenefitServiceV2 {

    private final ProgramService programService;
    private final WelfareService welfareService;
    private final UserServiceV2 userService;
    private final AreaService areaService;

    // 크롤링한 복지정보 저장.
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

            UserEntityV2 adminId = userService.validateAdmin(admin);      // 관리자 계정
            Wcategory wcategory = welfareService.getWcategoryByWcategoryId(wCategoryId);        // 복지 카테고리
            Regions region = areaService.validateRegionCode(hostId);    // 지역코드

            String imageUrl = "";
            if(region.getSigu_id().getName().equals("금천구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/geumcheon-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("구로구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/guro-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("서초구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seocho-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("강남구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangnam-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("강서구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/ganseo-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("관악구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gwanak-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("광진구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gwangjin-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("동작구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dongjak-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("영등포구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yeongdeungpo-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("송파구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/songpa-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("강동구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangdong-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("양천구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yangcheon-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("마포구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/mapo-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("서대문구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seodaemun-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("은평구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/eunpyeong-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("노원구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/nowon-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("도봉구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dobong-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("강북구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangbuk-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("성북구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/sungbuk-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("종로구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/jongno-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("중구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/jung-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("중랑구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/_jungnang-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("동대문구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dongdaemun-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("성동구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seongdong-gu.png?raw=true";
            } else if (region.getSigu_id().getName().equals("용산구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yongsan-gu.png?raw=true";
            } else if(region.getSigu_id().getName().equals("정부")) {
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
     * 앱 맞춤혜택에 보여질 복지 제목 리스트
     */
    public List<BenefitDTO.benefitTitleResponse> getBenefitTitleList(Regions regions, Regions government, String city,
                                                              String district, UserEntityV2 babyType, List<ScrapDTO.response> scrapList, String group) {

        // 자치구 복지정보.
        List<Welfare> welfareList = welfareService.findAllByWelfareById(regions);
        // 정부 복지정보.
        List<Welfare> governmentList = welfareService.findAllByWelfareById(government);
        // 프로그램 정보
        List<Program> programList = programService.findAllProgramByRegions(regions);


        // Response List.
        List<BenefitDTO.benefitTitleResponse> benefitList;

        Long categoryId = null;

        // 임신/육아 여부 확인.
        if (babyType.getBabyState() != null) {
            categoryId = 2L;                // 육아
        } else if (babyType.getBabyState() == null) {
            categoryId = 1L;                // 임신
        }

        benefitList = parseWelfareTitleByCustom(welfareList, city, district, categoryId, scrapList);
        benefitList.addAll(parseWelfareTitleByCustom(governmentList, "정부", "", categoryId, scrapList));
        benefitList.addAll(parseProgramTitleByGroupAndCustom(programList, city, district, scrapList, group));

        return benefitList;
    }

    /**
     * 앱 홈화면에 보여질 복지 제목 리스트, 요청받은 그룹에 맞는 정보 제공.
     */
    public List<BenefitDTO.benefitTitleResponse> getBenefitTitleListByGroup(Regions regions, Regions government, String city,
                                                                     String district, UserEntityV2 babyType, List<ScrapDTO.response> scrapList, String group) {

        // 자치구 복지정보.
        List<Welfare> welfareList = welfareService.findAllByWelfareById(regions);
        // 정부 복지정보.
        List<Welfare> governmentList = welfareService.findAllByWelfareById(government);
        // 프로그램 정보
        List<Program> programList = programService.findAllProgramByRegions(regions);

        // Response List.
        List<BenefitDTO.benefitTitleResponse> benefitList;

        Long categoryId = null;

        // 임신/육아 여부 확인.
        if (babyType.getBabyState() != null) {
            categoryId = 2L;                // 육아
        } else if (babyType.getBabyState() == null) {
            categoryId = 1L;                // 임신
        }

        benefitList = parseWelfareTitle(welfareList, city, district, categoryId, 1, scrapList);
        benefitList.addAll(parseWelfareTitle(governmentList, "정부", "", categoryId, 1, scrapList));
        benefitList.addAll(parseProgramTitleByGroup(programList, city, district, 1, scrapList, group));

        return benefitList;
    }

    /**
     * 앱 홈화면에 보여질 복지 제목 리스트, Most View
     */
    public List<BenefitDTO.benefitTitleResponse> getBenefitTitleListByMostView(Regions regions, String city,
                                                                        String district, UserEntityV2 babyType, List<ScrapDTO.response> scrapList) {

        // 자치구 복지정보.
        List<Welfare> welfareList = welfareService.findAllByView(regions);

        // 프로그램 정보
        List<Program> programList = programService.findByView(regions);

        // Response List.
        List<BenefitDTO.benefitTitleResponse> benefitList;

        Long categoryId = null;

        // 임신/육아 여부 확인.
        if (babyType != null) {
            categoryId = 1L;                // 임신
        } else if (babyType == null) {
            categoryId = 2L;                // 육아
        }

        if(programList.size() == 0) {
            benefitList = parseWelfareTitleByMostView(welfareList, city, district, categoryId, 5, scrapList);
        } else {
            benefitList = parseWelfareTitleByMostView(welfareList, city, district, categoryId, 3, scrapList);
            benefitList.addAll(parseProgramTitleByMostView(programList, city, district, 2, scrapList));
        }

        return benefitList;
    }

    /**
     * 웹 지역시 기준 복지 리스트
     */
    public List<BenefitDTO.titleResponseWeb> getBenefitTitleListWeb(Regions government, String reqCity, String reqDistrict) {

        // 전체 복지정보.
        List<Welfare> welfareList = welfareService.findAll();
        // 정부 복지정보.
        List<Welfare> governmentList = welfareService.findAllByWelfareById(government);
        // 프로그램 정보
        List<Program> programList = programService.findAll();

        // Response List.
        List<BenefitDTO.titleResponseWeb> benefitList;

        benefitList = parseWelfareTitleWeb(welfareList, reqCity, reqDistrict);
        benefitList.addAll(parseWelfareTitleWeb(governmentList, "정부", reqDistrict));
        benefitList.addAll(parseProgramTitleWeb(programList, reqCity, reqDistrict));

        return benefitList;
    }

    /**
     * App, Web
     * benefit_id로 상세 복지정보 조회.
     *
     * @return BenefitDTO.benefitResponse
     */
    public BenefitDTO.benefitResponse getBenefitDetail(Long benefit_id, String scrap, String group) {

        Welfare detailBenefit = welfareService.findById(benefit_id);

        String category = "";
        String city = detailBenefit.getHostId().getSido_id().getName();
        String district = detailBenefit.getHostId().getSigu_id().getName();
        boolean b_scrap = false;

        if (scrap.equals("true"))
            b_scrap = true;

        if (detailBenefit.getWCategoryId().getId() == 1L)
            category = "임산부";
        else if (detailBenefit.getWCategoryId().getId() == 2L) {
            category = "영유아";
        }

        BenefitDTO.benefitResponse build = BenefitDTO.benefitResponse.builder()
                .group("benefit")
                .benefitId(detailBenefit.getId())
                .category(category)
                .title(detailBenefit.getTitle())
                .field(detailBenefit.getField())
                .content(detailBenefit.getContent())
                .way(detailBenefit.getWay())
                .etc(detailBenefit.getEtc())
                .originalUrl(detailBenefit.getOriginalUrl())
                .applicationPeriod("")
                .trainingPeriod("")
                .city(city)
                .district(district)
                .imageUrl(detailBenefit.getImageUrl())
                .scrap(scrap)
                .build();

        return build;
    }

    /**
     * App, Web
     * program_id로 상세 복지정보 조회.
     * @return BenefitDTO.programResponse
     */
    public BenefitDTO.programResponse getProgramDetail(Long program_id, String scrap, String group) {

        Program detailProgram = programService.findById(program_id);

        String city = detailProgram.getCityName().getSido_id().getName();
        String district = detailProgram.getCityName().getSigu_id().getName();
        boolean b_scrap = false;

        if (scrap.equals("true"))
            b_scrap = true;

        BenefitDTO.programResponse build = BenefitDTO.programResponse.builder()
                .group("proram")
                .benefitId(detailProgram.getId())
                .title(detailProgram.getTitle())
                .applicationPeriod(detailProgram.getApplicationPeriod())
                .trainingPeriod(detailProgram.getTrainingPeriod())
                .category(detailProgram.getCategory())
                .city(city)
                .district(district)
                .imageUrl(detailProgram.getImageUrl())
                .scrap(scrap)
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

    /**
     * 사용자 타입에 맞는 지역시, 정부의 전체 혜택 중 n개의 값만 리턴
     */
    private List<BenefitDTO.benefitTitleResponse> parseWelfareTitle(List<Welfare> list, String city, String district,
                                                             Long categoryId, int count, List<ScrapDTO.response> scrapList) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();
        List<BenefitDTO.benefitTitleResponse> randomList = new ArrayList<>();

        for (Welfare welfare : list) {

            // 사용자의 임신 육아 여부에 일치하는 복지 정보만 저장.
            if (welfare.getWCategoryId().getId().equals(categoryId)) {
                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(welfare.getId())
                        .title(welfare.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod("")
                        .trainingPeriod("")
                        .imageUrl(welfare.getImageUrl())
                        .scrap("false")
                        .group("benefit")
                        .build();

                resultList.add(dto);
            }
        }

        Random random = new Random();
        int randomIndex = 0;

        if(list.size() < count) {
            log.info("count 크기 변경", list.size());
            count = list.size();
        }

        // 복지 리스트 중 랜덤으로 선택
        for (int i = 0; i < count; i++) {
            randomIndex = random.nextInt(resultList.size());
            randomList.add(resultList.get(randomIndex));
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
        for (ScrapDTO.response scrap : scrapList) {
            for (BenefitDTO.benefitTitleResponse randoom : randomList) {
                if (scrap.getWelfareId() == randoom.getBenefitId()) {
                    randoom.setScrap("true");
                }
            }
        }

        return randomList;
    }

    /**
     * 사용자 타입에 맞는 지역시, 정부의 전체 혜택 리턴
     */
    private List<BenefitDTO.benefitTitleResponse> parseWelfareTitleByCustom(List<Welfare> list, String city, String district,
                                                             Long categoryId, List<ScrapDTO.response> scrapList) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();

        for (Welfare welfare : list) {

            // 사용자의 임신 육아 여부에 일치하는 복지 정보만 저장.
            if (welfare.getWCategoryId().getId().equals(categoryId)) {
                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(welfare.getId())
                        .title(welfare.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod("")
                        .trainingPeriod("")
                        .imageUrl(welfare.getImageUrl())
                        .scrap("false")
                        .group("benefit")
                        .build();

                resultList.add(dto);
            }
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
        for (ScrapDTO.response scrap : scrapList) {
            for (BenefitDTO.benefitTitleResponse result : resultList) {
                if (scrap.getWelfareId() == result.getBenefitId()) {
                    result.setScrap("true");
                }
            }
        }

        return resultList;
    }

    /**
     * 프로그램 정보
     */
    private List<BenefitDTO.benefitTitleResponse> parseProgramTitle(List<Program> list, String city, String district,
                                                             UserEntityV2 babyType, int count, List<ScrapDTO.response> scrapList) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();
        List<BenefitDTO.benefitTitleResponse> randomList = new ArrayList<>();

        for (Program program : list) {

            log.info("status : true");

            BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                    .benefitId(program.getId())
                    .title(program.getTitle())
                    .city(city)
                    .district(district)
                    .imageUrl(program.getImageUrl())
                    .scrap("false")
                    .group("program")
                    .build();

            resultList.add(dto);
        }


        Random random = new Random();
        int randomIndex = 0;

        if (resultList.size() != 0) {
            // 복지 리스트 중 랜덤으로 선택
            for (int i = 0; i < count; i++) {
                randomIndex = random.nextInt(resultList.size());
                randomList.add(resultList.get(randomIndex));
            }
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
        for (ScrapDTO.response scrap : scrapList) {
            for (BenefitDTO.benefitTitleResponse randoom : randomList) {
                if (scrap.getWelfareId() == randoom.getBenefitId()) {
                    randoom.setScrap("true");
                }
            }
        }

        return randomList;
    }

    /**
     * 사용자 타입에 맞는 지역시, 정부의 전체 혜택 중 n개의 값만 리턴
     */
    private List<BenefitDTO.benefitTitleResponse> parseWelfareTitleByMostView(List<Welfare> list, String city, String district,
                                                             Long categoryId, int count, List<ScrapDTO.response> scrapList) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();
        List<BenefitDTO.benefitTitleResponse> respList = new ArrayList<>();

        for (Welfare welfare : list) {
            log.info(String.valueOf(welfare.getView()));

            // 사용자의 임신 육아 여부에 일치하는 복지 정보만 저장.
            //if (welfare.getWCategoryId().getId().equals(categoryId)) {
                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(welfare.getId())
                        .title(welfare.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod("")
                        .trainingPeriod("")
                        .imageUrl(welfare.getImageUrl())
                        .scrap("false")
                        .group("benefit")
                        .build();

                resultList.add(dto);
            //}
        }

        // 복지 리스트 중 앞 3개의 정보 선택
        for (int i = 0; i < count; i++) {
            respList.add(resultList.get(i));
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
            for (ScrapDTO.response scrap : scrapList) {
                for (BenefitDTO.benefitTitleResponse resp : respList) {
                    if (scrap.getWelfareId().equals(resp.getBenefitId())) {
                        log.info("scrap " + scrap.getWelfareId());
                        log.info("benefit " + resp.getBenefitId());
                        resp.setScrap("true");
                    }
                }
            }

        return respList;
    }

    /**
     * 프로그램 정보
     */
    private List<BenefitDTO.benefitTitleResponse> parseProgramTitleByMostView(List<Program> list, String city, String district,
                                                                       int count, List<ScrapDTO.response> scrapList) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();
        List<BenefitDTO.benefitTitleResponse> respList = new ArrayList<>();

        for (Program program : list) {
            BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                    .benefitId(program.getId())
                    .title(program.getTitle())
                    .city(city)
                    .district(district)
                    .imageUrl(program.getImageUrl())
                    .scrap("false")
                    .group("program")
                    .build();

            resultList.add(dto);
        }

        if(list.size() < 2) {
            log.info("count 크기 변경", list.size());
            count = list.size();
        }

        // 복지 리스트 중 앞 3개의 정보 선택
        for (int i = 0; i < count; i++) {
            respList.add(resultList.get(i));
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
            for (ScrapDTO.response scrap : scrapList) {
                for (BenefitDTO.benefitTitleResponse resp : respList) {
                    if (scrap.getWelfareId() == resp.getBenefitId()) {
                        resp.setScrap("true");
                    }
                }
            }

        return respList;
    }

    /**
     * 프로그램 정보, 요청한 그룹의 대한 데이터 제공.
     */
    private List<BenefitDTO.benefitTitleResponse> parseProgramTitleByGroup(List<Program> list, String city, String district,
                                                                    int count, List<ScrapDTO.response> scrapList, String group) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();
        List<BenefitDTO.benefitTitleResponse> randomList = new ArrayList<>();

        for (Program program : list) {

            if (program.getCategory().contains(group)) {
                log.info("status : true");

                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(program.getId())
                        .title(program.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod(program.getApplicationPeriod())
                        .trainingPeriod(program.getTrainingPeriod())
                        .imageUrl(program.getImageUrl())
                        .scrap("false")
                        .group("program")
                        .build();

                resultList.add(dto);
            }
        }

        Random random = new Random();
        int randomIndex = 0;

        if (resultList.size() != 0) {
            // 복지 리스트 중 랜덤으로 선택
            for (int i = 0; i < count; i++) {
                randomIndex = random.nextInt(resultList.size());
                randomList.add(resultList.get(randomIndex));
            }
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
        for (ScrapDTO.response scrap : scrapList) {
            for (BenefitDTO.benefitTitleResponse randoom : randomList) {
                if (scrap.getWelfareId() == randoom.getBenefitId()) {
                    randoom.setScrap("true");
                }
            }
        }
        return randomList;
    }

    /**
     * 맞춤혜택, 프로그램 정보, 요청한 그룹의 대한 데이터 제공.
     */
    private List<BenefitDTO.benefitTitleResponse> parseProgramTitleByGroupAndCustom(List<Program> list, String city, String district,
                                                                    List<ScrapDTO.response> scrapList, String group) {
        List<BenefitDTO.benefitTitleResponse> resultList = new ArrayList<>();

        for (Program program : list) {

            if (group.equals("전체")) {

                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(program.getId())
                        .title(program.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod(program.getApplicationPeriod())
                        .trainingPeriod(program.getTrainingPeriod())
                        .imageUrl(program.getImageUrl())
                        .scrap("false")
                        .group("program")
                        .build();

                resultList.add(dto);
            }
            else if (program.getCategory().contains(group)) {

                BenefitDTO.benefitTitleResponse dto = BenefitDTO.benefitTitleResponse.builder()
                        .benefitId(program.getId())
                        .title(program.getTitle())
                        .city(city)
                        .district(district)
                        .applicationPeriod(program.getApplicationPeriod())
                        .trainingPeriod(program.getTrainingPeriod())
                        .imageUrl(program.getImageUrl())
                        .scrap("false")
                        .group("program")
                        .build();

                resultList.add(dto);
            }
        }

        // 응답할 복지 리스트의 스크랩 여부 확인.
        for (ScrapDTO.response scrap : scrapList) {
            for (BenefitDTO.benefitTitleResponse result : resultList) {
                if (scrap.getWelfareId() == result.getBenefitId()) {
                    result.setScrap("true");
                }
            }
        }
        return resultList;
    }

    private List<BenefitDTO.titleResponseWeb> parseWelfareTitleWeb(List<Welfare> list, String reqCity, String reqDistrict) {
        List<BenefitDTO.titleResponseWeb> resultList = new ArrayList<>();

        for (Welfare welfare : list) {
            String sidoName = welfare.getHostId().getSido_id().getName();
            String siguName = welfare.getHostId().getSigu_id().getName();

            if (reqDistrict.isEmpty()) {
                if (sidoName.equals(reqCity)) {
                    String district = welfare.getHostId().getUmd_id().getName();
                    String wType = welfare.getWCategoryId().getName();
                    BenefitDTO.titleResponseWeb dto = BenefitDTO.titleResponseWeb.builder()
                            .benefitId(welfare.getId())
                            .title(welfare.getTitle())
                            .type(wType)
                            .city(reqCity)
                            .district(district)
                            .applicationPeriod("")
                            .trainingPeriod("")
                            .imageUrl(welfare.getImageUrl())
                            .group("benefit")
                            .build();

                    resultList.add(dto);
                }
            } else {
                if (siguName.equals(reqDistrict)) {
                    String district = welfare.getHostId().getUmd_id().getName();
                    String wType = welfare.getWCategoryId().getName();
                    BenefitDTO.titleResponseWeb dto = BenefitDTO.titleResponseWeb.builder()
                            .benefitId(welfare.getId())
                            .title(welfare.getTitle())
                            .type(wType)
                            .city(reqCity)
                            .district(district)
                            .imageUrl(welfare.getImageUrl())
                            .group("benefit")
                            .build();

                    resultList.add(dto);
                }
            }
        }
        return resultList;
    }

    private List<BenefitDTO.titleResponseWeb> parseProgramTitleWeb(List<Program> list, String reqCity, String reqDistrict) {
        List<BenefitDTO.titleResponseWeb> resultList = new ArrayList<>();

        for (Program program : list) {

            String sidoName = program.getCityName().getSido_id().getName();
            String siguName = program.getCityName().getSigu_id().getName();

            if (reqDistrict.isEmpty()) {
                if (sidoName.equals(reqCity)) {

                    String district = program.getCityName().getUmd_id().getName();
                    String wType = program.getCategory();

                    BenefitDTO.titleResponseWeb dto = BenefitDTO.titleResponseWeb.builder()
                            .benefitId(program.getId())
                            .title(program.getTitle())
                            .type(wType)
                            .city(reqCity)
                            .district(district)
                            .applicationPeriod(program.getApplicationPeriod())
                            .trainingPeriod(program.getTrainingPeriod())
                            .imageUrl(program.getImageUrl())
                            .group("program")
                            .build();

                    resultList.add(dto);
                }
            } else {
                if (siguName.equals(reqDistrict)) {

                    String district = program.getCityName().getUmd_id().getName();
                    String wType = program.getCategory();

                    BenefitDTO.titleResponseWeb dto = BenefitDTO.titleResponseWeb.builder()
                            .benefitId(program.getId())
                            .title(program.getTitle())
                            .type(wType)
                            .city(reqCity)
                            .district(district)
                            .imageUrl(program.getImageUrl())
                            .group("program")
                            .build();

                    resultList.add(dto);
                }
            }
        }
        return resultList;
    }
}
