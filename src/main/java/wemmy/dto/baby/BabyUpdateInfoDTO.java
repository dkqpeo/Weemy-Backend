package wemmy.dto.baby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BabyUpdateInfoDTO {

    private String name;
    private LocalDate birthday;
    private String type;
}
