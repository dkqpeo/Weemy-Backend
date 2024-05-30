package wemmy.domain.area.city;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "sido_areas")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SidoAreas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sido_id")
    private Long id;

    @Column(unique = true)
    private String adm_code;

    @Column(unique = true)
    private String name;
}
