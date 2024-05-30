package wemmy.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.user.LoginDTO;
import wemmy.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO.response> login(@RequestBody LoginDTO.request dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();

        LoginDTO.response result = userService.login(email, password);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
