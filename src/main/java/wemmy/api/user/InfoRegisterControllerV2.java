package wemmy.api.user;

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
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.ResponseDTO;
import wemmy.dto.user.UserRegisterDTOV2;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserServiceV2;


@Tag(name = "UserV2", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/user/v2")
public class InfoRegisterControllerV2 {

    private final AreaService areaService;
    private final UserServiceV2 userServiceV2;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * 온보딩 시 입력 정보 저장
     */
    @Tag(name = "UserV2")
    @Operation(summary = "온보딩 시 입력받은 정보 저장", description = "city : 시, 도, district : 구, 군, userState : 결혼. 임신. 출산 탭, babyState : 자녀상태, characteristic : 대상특성")
    @PostMapping("/register/info")
    public ResponseEntity<ResponseDTO> insertV2(@RequestBody UserRegisterDTOV2 dto, HttpServletRequest httpServletRequest) {

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userId);

        SiguAreas area = areaService.validateArea(dto.getCity(), dto.getDistrict());

        userServiceV2.updateArea(userId, area);
        userServiceV2.updateUserInfo(userId, dto);

        return new ResponseEntity<>(ResponseDTO.of("정보 등록완료.") , HttpStatus.OK);
    }
}
