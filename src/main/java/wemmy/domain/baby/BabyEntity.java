package wemmy.domain.baby;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntityV2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "BABY")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BabyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_id")
    private Long id;

    private String name;
    //private String name2;

    private LocalDate birthday;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BabyType type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    UserEntityV2 user;

    public void updateBabyInfo(String name, LocalDate birthday, BabyType type){
        if(name != null)
            this.name = name;
        if(birthday != null)
            this.birthday=birthday;
        if(type != null)
            this.type = type;
    }
}
