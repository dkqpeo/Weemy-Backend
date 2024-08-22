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
//@IdClass(ScrapPK.class)
@NoArgsConstructor
@AllArgsConstructor
public class ScrapEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    UserEntity user_id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "welfare_id", referencedColumnName = "welfare_id")
    Welfare welfare_id;

}