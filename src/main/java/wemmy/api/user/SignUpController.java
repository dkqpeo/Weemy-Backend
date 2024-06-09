package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.ResponseDTO;
import wemmy.dto.user.SignUpDTO;
import wemmy.service.user.UserService;

import java.time.LocalDateTime;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Tag(name = "User")
    @Operation(summary = "회원가입 이메일 중복 확인 API", description = "회원가입 시 이메일 중복 여부 확인")
    @GetMapping("/validate/{email}")
    public ResponseEntity<ResponseDTO> validateEmail(@PathVariable("email") String email) {
        userService.validateEmail(email);

        return new ResponseEntity<>(ResponseDTO.of("사용 가능한 이메일입니다.") ,HttpStatus.OK);
    }

    @Tag(name = "User")
    @Operation(summary = "사용자 회원가입 API", description = "wemmy 서비스 이용을 위한 회원가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> signUp(@RequestBody SignUpDTO dto) {

        UserEntity user = UserEntity.builder()
                .userType(UserType.LOCAL)
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();

        userService.signUp(user);

        return new ResponseEntity<>(ResponseDTO.of("회원가입 완료.") ,HttpStatus.CREATED);
    }
}
