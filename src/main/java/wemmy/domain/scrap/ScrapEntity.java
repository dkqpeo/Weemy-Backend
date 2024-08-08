package wemmy.domain.scrap;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.common.BaseTimeEntity;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;

@Entity (name = "SCRAP")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    UserEntity user_id;

    @Id
    @ManyToOne
    @JoinColumn(name = "welfare_id", referencedColumnName = "welfare_id")
    Welfare welfare_id;

}