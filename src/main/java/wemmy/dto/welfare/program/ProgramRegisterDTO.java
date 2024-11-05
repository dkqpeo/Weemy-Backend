package wemmy.dto.welfare.program;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class ProgramRegisterDTO {

    private String name;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate birthday;

    private String address;

    private String addressDetail;

    private String phone;

    private String email;

}
