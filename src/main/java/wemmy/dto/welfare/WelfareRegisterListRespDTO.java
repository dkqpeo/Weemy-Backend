package wemmy.dto.welfare;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class WelfareRegisterListRespDTO {

    @Getter
    @Builder
    public static class welfareRegister {
        private Long registerId;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
        private LocalDateTime registerDate;
        private String title;
        private String name;
        private String phone;
        private String email;
        private String address;
        private String addressDetail;
    }

    @Getter @Builder
    public static class response{
        private String group;
        private List<welfareRegister> data;
    }
}
