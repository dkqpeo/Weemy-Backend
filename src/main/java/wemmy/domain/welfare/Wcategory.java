package wemmy.domain.welfare;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity (name = "w_category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_category_id")
    private Long id;

    private String name;    // 임산부, 영유아
}
