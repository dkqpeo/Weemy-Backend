package wemmy.dto.benefit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BenefitDTO {

    @Getter @Builder
    public static class response{
        private Long benefit_id;
        private int unique_id;
        private String admin_id;        // 관리자 id
        private Long w_category_id;      // 1 임신, 2 영유아
        private String host_id;         // region_cd
        private String title;           // 복지 제목
        private String field;           // 지원대상
        private String content;         // 내용
        private String way;             // 신청방법
        private String inquiry;         // 문의처
        private String etc;             // 기타
        private String original_url;    // 원본 url
        private String city;            // 시 ex) 서울특별시
        private String district;        // 지역 ex) 강남구
    }
}
