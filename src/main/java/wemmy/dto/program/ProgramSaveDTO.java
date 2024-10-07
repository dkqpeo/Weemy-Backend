package wemmy.dto.program;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProgramSaveDTO {

    private String title;               // 프로그램 이름
    private String aplicationPeriod;    // 신청 기간
    private String trainingPeriod;      // 교육 기간
    private String category;            // 카테고리 ['임신준비', '임신', '출산·육아']
    private String cityName;            // 지역구
    private String adminId;             // 관리자 ID
}
