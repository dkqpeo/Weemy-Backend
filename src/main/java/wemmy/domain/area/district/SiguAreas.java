package wemmy.domain.area.district;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.city.SidoAreas;

@Entity(name = "sigu_areas")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiguAreas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sigu_id")
    private Long id;

    @Column(unique = false)
    private String adm_code;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sido_id", referencedColumnName = "sido_id")
    SidoAreas sido_id;
}
