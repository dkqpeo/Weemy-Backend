package wemmy.domain.welfare;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntity;

@Entity(name = "program")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    private String title;               // 프로그램 이름

    private String aplicationPeriod;    // 신청 기간

    private String trainingPeriod;      // 교육 기간

    private String category;            // 카테고리 ['임신준비', '임신', '출산·육아']

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "city_name", referencedColumnName = "region_id")
    Regions cityName;                   // 지역구

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    UserEntity adminId;                 // 관리자 ID

    @Column(name = "image_url", length = 2083)
    private String imageUrl;            // 이미지 url
}
