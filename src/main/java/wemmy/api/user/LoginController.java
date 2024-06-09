package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.user.LoginDTO;
import wemmy.service.user.UserService;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class LoginController {

    private final UserService userService;

    @Tag(name = "User")
    @Operation(summary = "사용자 로그인 API", description = "wemmy 서비스 이용을 위한 로그인 API")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO.loginResponse> login(@RequestBody LoginDTO.loginRequest dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();

        LoginDTO.loginResponse result = userService.login(email, password);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
