package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.user.LoginDTO;
import wemmy.service.user.UserService;
import wemmy.service.user.UserServiceV2;

@Tag(name = "UserV2", description = "회원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2")
public class LoginControllerV2 {

    private final UserServiceV2 userServiceV2;

    @Tag(name = "UserV2")
    @Operation(summary = "사용자 로그인 API", description = "wemmy 서비스 이용을 위한 로그인 API")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO.loginResponse> loginV2(@RequestBody LoginDTO.loginRequest dto,
                                                        HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        String email = dto.getEmail();
        String password = dto.getPassword();

        LoginDTO.loginResponse result = userServiceV2.login(email, password);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
