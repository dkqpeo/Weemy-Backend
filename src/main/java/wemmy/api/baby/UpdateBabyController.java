package wemmy.api.baby;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.ResponseDTO;
import wemmy.dto.baby.BabyUpdateInfoDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.baby.BabyService;

@Tag(name = "Baby", description = "아기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/baby")
public class UpdateBabyController {

    private final BabyService babyService;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "Baby")
    @Operation(summary = "아기정보 수정 API", description = "아기 정보 수정. 임신 -> 육아, 태명 -> 이름 등.")
    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateBabyInfo(@RequestBody BabyUpdateInfoDTO dto, HttpServletRequest httpServletRequest) {

        /*// 헤더의 토큰 정보 검증
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenProvider.validateToken(accessToken);

        // 토큰의 사용자 id(PK) 조회
        Long userId = tokenProvider.getUserIdFromClaims(accessToken);*/

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        babyService.updateBabyInfo(userId, dto);

        return new ResponseEntity<>(ResponseDTO.of("아기 정보 수정완료.") , HttpStatus.OK);
    }
}
