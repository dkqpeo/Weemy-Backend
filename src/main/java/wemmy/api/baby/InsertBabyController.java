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
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.ResponseDTO;
import wemmy.dto.baby.BabyInsertDTO;
import wemmy.global.security.CustomUserDetails;
import wemmy.service.baby.BabyService;
import wemmy.service.user.UserServiceV2;

import java.time.LocalDateTime;

//@Tag(name = "Baby", description = "아기 관련 API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/wemmy/baby")
public class InsertBabyController {

    private final BabyService babyService;
    private final UserServiceV2 userService;

    @Tag(name = "Baby", description = "아기 관련 API")
    @Operation(summary = "아기정보 추가", description = "dateFormat : yyyy-mm-dd, type : PREGNANCY, PARENTING (대문자로 작성)")
    //@PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insert(@RequestBody BabyInsertDTO dto,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        UserEntityV2 user = userService.findByUserId(userId);

        BabyEntity baby = BabyEntity.builder()
                .name(dto.getName())
                .birthday(dto.getBirthday())
                .createdAt(LocalDateTime.now())
                .type(BabyType.from(dto.getType()))
                .user(user)
                .build();
        babyService.insert(baby);

        return new ResponseEntity<>(ResponseDTO.of("아기 정보 등록완료.") , HttpStatus.OK);
    }
}
