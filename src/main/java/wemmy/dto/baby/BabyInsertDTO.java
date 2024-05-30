package wemmy.dto.baby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BabyInsertDTO {

    private String name;
    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate birthday;
    private String type;
}

/*
public class BabyInsertDTO {

    private String name1;
    private String name2;
    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate birthday;
    private String type;
}
*/
