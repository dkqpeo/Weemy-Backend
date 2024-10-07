package wemmy.dto.user;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wemmy.global.converter.StringListConverter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTOV2 {

    private String city;
    private String district;
    private List<String> topic;         // 관심 주제 (임신 준비, 임신, 출산, 양육)
    private String gender;
    private List<String> userState;     // 결혼. 임신. 출산 (예비부부, 신혼부부, 홀벌이, 임신계획, 난임 등..)
    private Boolean baby;                // 자녀 유무
    private List<String> babyState;     // 자녀 상태 ["영유아", "어린이집", "유치원", "성인", "초등학생", "중학생", "고등학생"]
    private List<String> characteristic;    // 대상특성 (해당 없음, 다문화 가정 등..)
    private int familyCount;        // 가구원 수
    private int monthlyImcome;      // 가구 월소득
}