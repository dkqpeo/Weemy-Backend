package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.domain.area.district.SiguAreas;
import wemmy.dto.user.UpdateCityDTO;
import wemmy.global.security.CustomUserDetails;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserServiceV2;

@Tag(name = "UserV2", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2")
public class UpdateCityControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final AreaService areaService;

    @Tag(name = "UserV2")
    @Operation(summary = "회원 거주지 변경 API", description = "city : 시, 도, district : 구, 군")
    @PostMapping("/update/city")
    public ResponseEntity<String> updateCity(@RequestBody UpdateCityDTO dto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        SiguAreas area = areaService.validateArea(dto.getCity(), dto.getDistrict());

        userServiceV2.updateArea(userId, area);
        return ResponseEntity.ok("정보수정 완료.");
    }
}
