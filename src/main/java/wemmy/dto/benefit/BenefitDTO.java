package wemmy.dto.benefit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BenefitDTO {

    @Getter @Builder
    public static class response{
        private Long benefitId;
        private Long wCategoryId;     // 1 임신, 2 영유아
        private String title;           // 복지 제목
        private String field;           // 지원대상
        private String content;         // 내용
        private String way;             // 신청방법
        private String etc;             // 연락처
        private String originalUrl;    // 원본 url
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
    }

    /**
     * 앱 홈화면 복지 제목 리스트 응답.
     */
    @Getter @Builder
    public static class titleResponse{
        private Long benefitId;
        private String title;           // 복지 제목
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
    }

    /**
     * 웹 복지 제목 리스트 응답.
     */
    @Getter @Builder
    public static class titleResponseWeb{
        private Long benefitId;
        private String title;           // 복지 제목
        private String type;            // 임신, 육아
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
        private String imageUrl;        // 이미지 url
    }
}
