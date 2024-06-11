package wemmy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.area.district.SiggAreas;
import wemmy.dto.user.UpdateCityDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user")
public class UpdateCityController {

    private final UserService userService;
    private final AreaService areaService;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "User")
    @Operation(summary = "회원 거주지 변경 API", description = "city : 시, 도, district : 구, 군")
    @PostMapping("/update/city")
    public ResponseEntity<String> updateCity(@RequestBody UpdateCityDTO dto, HttpServletRequest request) {

        Long userId = getUserIDByToken.getUserID(request);
        SiggAreas area = areaService.validateArea(dto.getCity(), dto.getDistrict());

        userService.updateArea(userId, area);
        return ResponseEntity.ok("정보수정 완료.");
    }
}
