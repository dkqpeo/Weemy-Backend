package wemmy.api.user;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/validate/{email}")
    public ResponseEntity<ResponseDTO> validateEmail(@PathVariable("email") String email) {
        userService.validateEmail(email);

        return new ResponseEntity<>(ResponseDTO.of("사용 가능한 이메일입니다.") ,HttpStatus.OK);
    }

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
