package wemmy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String name;
    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate birthday;
    private String type;
    private String city;
    private String district;
}