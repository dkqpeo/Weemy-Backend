package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.ResponseDTO;
import wemmy.dto.user.UpdateDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.service.user.UserServiceV2;

import java.io.IOException;

@Tag(name = "UserV2", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2")
public class UpdatePasswordControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "UserV2")
    @Operation(summary = "회원 비밀번호 변경 API", description = "회원 비밀번호 변경.")
    @PostMapping("/update/password")
    public ResponseEntity<ResponseDTO> updatePassword(@RequestBody UpdateDTO.request dto, HttpServletRequest httpServletRequest) throws IOException {

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        userServiceV2.updatePassword(userId, dto.getOldPassword(), passwordEncoder.encode(dto.getNewPassword()));

        // 나중에 소셜 사용자는 비밀번호 변경할 수 없도록 수정필요.
        return new ResponseEntity<>(ResponseDTO.of("비밀번호 변경완료.") , HttpStatus.OK);
    }
}
