package wemmy.dto.welfare.benefit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wemmy.dto.facility.FacilityDTO;

import java.util.List;

public class BenefitDTO {

    @Getter
    @Builder
    public static class titleListResponse {
        private String category;
        private List<BenefitDTO.benefitTitleResponse> data;
    }

    @Getter
    @Builder
    public static class response {
        private String group;           // benefit, program
        private Long benefitId;
        private Long wCategoryId;     // 1 임신, 2 영유아
        private String title;           // 복지 제목
        private String field;           // 지원대상
        private String content;         // 내용
        private String way;             // 신청방법
        private String etc;             // 연락처
        private String originalUrl;    // 원본 url
        private String applicationPeriod;    // 신청 기간
        private String trainingPeriod;      // 교육 기간
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
        private String scrap;           // 스크랩 여부
        //private boolean scrap;           // 스크랩 여부
    }

    @Getter
    @Builder
    public static class benefitResponse {
        private String group;           // benefit, program
        private Long benefitId;
        private String category;     // 임신, 영유아
        private String title;           // 복지 제목
        private String field;           // 지원대상
        private String content;         // 내용
        private String way;             // 신청방법
        private String etc;             // 연락처
        private String originalUrl;    // 원본 url
        private String applicationPeriod;    // 신청 기간
        private String trainingPeriod;      // 교육 기간
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
        private String scrap;           // 스크랩 여부
        //private boolean scrap;           // 스크랩 여부
    }

    @Getter
    @Builder
    public static class programResponse {
        private String group;           // benefit, program
        private Long benefitId;
        private String title;           // 복지 제목
        private String applicationPeriod;    // 신청 기간
        private String trainingPeriod;      // 교육 기간
        private String category;            // 카테고리 ['임신준비', '임신', '출산·육아']
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
        private String scrap;           // 스크랩 여부
        //private boolean scrap;           // 스크랩 여부
    }

    /**
     * APP 홈화면 복지 제목 리스트 응답.
     */
    @Getter
    @Setter
    @Builder
    public static class benefitTitleResponse {
        private Long benefitId;
        private String title;           // 복지 제목
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String applicationPeriod;    // 신청 기간
        private String trainingPeriod;      // 교육 기간
        private String imageUrl;        // 이미지 url
        private String scrap;           // 스크랩 여부
        //private boolean scrap;           // 스크랩 여부
        private String group;           // benefit, program
    }

    /**
     * WEB 복지 제목 리스트 응답.
     */
    @Getter
    @Builder
    public static class titleResponseWeb {
        private Long benefitId;
        private String title;           // 복지 제목
        private String type;            // 임신, 육아
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String applicationPeriod;    // 신청 기간
        private String trainingPeriod;      // 교육 기간
        private String imageUrl;        // 이미지 url
        private String group;           // benefit, program
    }
}
