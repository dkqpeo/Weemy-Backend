package wemmy.domain.scrap;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.common.BaseTimeEntity;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Welfare;

@Entity (name = "SCRAPV2")
@Getter
@Builder
//@IdClass(ScrapPK.class)
@NoArgsConstructor
@AllArgsConstructor
public class ScrapEntityV2 extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @Column(name = "`group`", length = 20)
    private String group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    UserEntityV2 user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "welfare_id", referencedColumnName = "welfare_id")
    Welfare welfare_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    Program program_id;


}