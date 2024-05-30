package wemmy.domain.area;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.area.district.UmdAreas;

@Entity(name = "regions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Regions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(unique = true)
    private String region_cd;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sido_id", referencedColumnName = "sido_id")
    SidoAreas sido_id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sigg_id", referencedColumnName = "sigg_id")
    SiggAreas sigg_id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "umd_id", referencedColumnName = "umd_id")
    UmdAreas umd_id;
}
