package wemmy.api.baby;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.ResponseDTO;
import wemmy.dto.baby.BabyUpdateInfoDTO;
import wemmy.global.security.CustomUserDetails;
import wemmy.service.baby.BabyService;

//@Tag(name = "Baby", description = "아기 관련 API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/wemmy/baby")
public class UpdateBabyController {

    private final BabyService babyService;

    @Tag(name = "Baby")
    @Operation(summary = "아기정보 수정 API", description = "아기 정보 수정. 임신 -> 육아, 태명 -> 이름 등.")
    //@PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateBabyInfo(@RequestBody BabyUpdateInfoDTO dto,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        babyService.updateBabyInfo(userId, dto);

        return new ResponseEntity<>(ResponseDTO.of("아기 정보 수정완료.") , HttpStatus.OK);
    }
}
