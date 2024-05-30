package wemmy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private String userType;
    private String email;
    private String password;
    private String role;
}
