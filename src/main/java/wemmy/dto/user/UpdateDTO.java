package wemmy.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UpdateDTO {
    @Getter
    public static class request{
        private String oldPassword;
        private String newPassword;
    }
}