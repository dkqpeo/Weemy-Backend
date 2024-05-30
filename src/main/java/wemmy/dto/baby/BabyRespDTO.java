package wemmy.dto.baby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BabyRespDTO {

    private String name;
    private LocalDate birthday;
    private BabyType type;

    public BabyRespDTO(BabyEntity baby) {
        this.name = baby.getName();
        this.birthday = baby.getBirthday();
        this.type = baby.getType();
    }
}
