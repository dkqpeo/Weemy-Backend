package wemmy.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateDTO {
    @Getter
    public static class request{
        private String oldPassword;
        private String newPassword;
    }
}