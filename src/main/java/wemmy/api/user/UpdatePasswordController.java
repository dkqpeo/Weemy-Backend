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
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.service.user.UserService;

import java.io.IOException;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class UpdatePasswordController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "User")
    @Operation(summary = "회원 비밀번호 변경 API", description = "회원 비밀번호 변경.")
    @PostMapping("/update/password")
    public ResponseEntity<ResponseDTO> updatePassword(@RequestBody UpdateDTO.request dto, HttpServletRequest httpServletRequest) throws IOException {

        /*// 헤더의 토큰 정보 검증
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenProvider.validateToken(accessToken);

        // 토큰의 사용자 id(PK) 조회
        Long userId = tokenProvider.getUserIdFromClaims(accessToken);*/

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        userService.updatePassword(userId, dto.getOldPassword(), passwordEncoder.encode(dto.getNewPassword()));

        // 나중에 소셜 사용자는 비밀번호 변경할 수 없도록 수정필요.
        return new ResponseEntity<>(ResponseDTO.of("비밀번호 변경완료.") , HttpStatus.OK);
    }
}
