package wemmy.dto.area.openApi;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OpenApiRespDTO {
    private String region_cd;     // 지역코드
    private String sido_cd;       // 시, 도
    private String sigg_cd;       // 구, 군
    private String umd_cd;        // 읍, 면, 동
    private String locallow_nm;   // 구(읍, 면, 동) 이름
}
