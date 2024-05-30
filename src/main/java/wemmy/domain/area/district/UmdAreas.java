package wemmy.domain.area.district;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity (name = "umd_areas")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmdAreas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umd_id")
    private Long id;

    @Column(unique = true)
    private String adm_code;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sigg_id", referencedColumnName = "sigg_id")
    SiggAreas sigg_id;

}
