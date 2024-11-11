package wemmy.domain.facility;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.UserEntity;

@Entity(name = "facility")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    @Column
    private String facilityName;

    @Column
    private String address;

    @Column
    private String tel;

    @Column
    private double longitude;                  // 경도

    @Column
    private double latitude;                   // 위도

    @Column
    private String categoryMain;            // 산후조리, 육사시설, 의료시설, 행정시설

    @Column
    private String categorySub;

    @Column
    private String operatingHours;          // 영업시간

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    UserEntity adminId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "district", referencedColumnName = "sigg_id")
    SiggAreas district;

}
