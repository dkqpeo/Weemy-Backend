package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.ResponseDTO;
import wemmy.dto.user.SignUpDTO;
import wemmy.service.user.UserServiceV2;

import java.time.LocalDateTime;

@Tag(name = "UserV2", description = "회원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2")
public class SignUpControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final PasswordEncoder passwordEncoder;

    @Tag(name = "UserV2")
    @Operation(summary = "회원가입 이메일 중복 확인 API", description = "회원가입 시 이메일 중복 여부 확인")
    @GetMapping("/validate/{email}")
    public ResponseEntity<ResponseDTO> validateEmailV2(@PathVariable("email") String email, HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        userServiceV2.validateEmail(email);
        return new ResponseEntity<>(ResponseDTO.of("사용 가능한 이메일입니다.") ,HttpStatus.OK);
    }

    @Tag(name = "UserV2")
    @Operation(summary = "사용자 회원가입 API", description = "wemmy 서비스 이용을 위한 회원가입 API / email, password만 전달")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> signUpV2(@RequestBody SignUpDTO dto, HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        UserEntityV2 user = UserEntityV2.builder()
                .userType(UserType.LOCAL)
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();

        userServiceV2.signUp(user);

        return new ResponseEntity<>(ResponseDTO.of("회원가입 완료.") ,HttpStatus.CREATED);
    }
}
