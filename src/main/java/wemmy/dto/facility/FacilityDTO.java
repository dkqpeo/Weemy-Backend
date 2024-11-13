package wemmy.dto.facility;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class FacilityDTO {
    @Getter
    @Builder
    public static class response {
        //private String facilitiesData;
        private List<detailResponse> facilitiesData;
    }

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
        private double longitude;
        private double latitude;

    }

    @Getter
    @Builder
    public static class detailResponse {
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
        private String facilitiesData;
        private String category;
        private List<facilityTitle> data;
    }
}
