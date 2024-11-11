package wemmy.dto.facility;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FacilityDTO {

    @Getter
    @Builder
    public static class facilityDetail {
        private Long facilityId;
        private String facilityName;
        private String address;
        private String tel;
        private String categorySub;
        private String city;
        private String district;
        private String operatingHours;
    }

    @Getter
    @Builder
    public static class response {
        private String category;
        private List<facilityDetail> data;
    }

    @Getter
    @Builder
    public static class facilityTitle {
        private Long facilityId;
        private String facilityName;
        private String city;
        private String district;
        private String operatingHours;
    }

    @Getter
    @Builder
    public static class titleResponse {
        private String category;
        private List<facilityTitle> data;
    }
}
