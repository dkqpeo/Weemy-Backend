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

    private String adm_code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "sigu_id", referencedColumnName = "sigu_id")
    SiguAreas sigu_id;

}
