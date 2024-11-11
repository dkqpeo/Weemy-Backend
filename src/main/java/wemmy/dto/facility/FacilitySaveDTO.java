package wemmy.dto.facility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class FacilitySaveDTO {

    private String facilityName;

    private String address;

    private String tel;

    private double longitude;               // 경도

    private double latitude;                // 위도

    private String categoryMain;            // 산후조리, 육사시설, 의료시설, 행정시설

    private String categorySub;

    private String operatingHours;          // 영업시간

    private String adminId;                 // 관리자 ID

    private String district;                // 지역구
}
