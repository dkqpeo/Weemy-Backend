package wemmy.api.user.old;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.UserEntityV2;
import wemmy.dto.ResponseDTO;
import wemmy.dto.user.UserRegisterDTO;
import wemmy.dto.user.UserRegisterDTOV2;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;
import wemmy.service.area.AreaService;
import wemmy.service.baby.BabyService;
import wemmy.service.user.UserService;
import wemmy.service.user.UserServiceV2;

import java.time.LocalDateTime;

//@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/wemmy/user")
public class InfoRegisterController {

    private final BabyService babyService;
    private final AreaService areaService;
    private final UserService userService;
    private final UserServiceV2 userServiceV2;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * 온보딩 시 입력받은 아기, 지역정보 저장
     */
    /*@Tag(name = "User")
    @Operation(summary = "온보딩 시 입력받은 아기, 지역정보 저장", description = "dateFormat : yyyy-mm-dd, type : PREGNANCY, PARENTING (대문자로 작성), city : 시, 도, district : 구, 군")
    @PostMapping("/register/info")*/
    public ResponseEntity<ResponseDTO> insert(@RequestBody UserRegisterDTO dto, HttpServletRequest httpServletRequest) {

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        UserEntity user = userService.findByUserId(userId);

        SiggAreas area = areaService.validateArea(dto.getCity(), dto.getDistrict());

        BabyEntity baby = BabyEntity.builder()
                .name(dto.getName())
                .birthday(dto.getBirthday())
                .createdAt(LocalDateTime.now())
                .type(BabyType.from(dto.getType()))
                .user(user)
                .build();

        babyService.insert(baby);
        userService.updateArea(userId, area);

        return new ResponseEntity<>(ResponseDTO.of("정보 등록완료.") , HttpStatus.OK);
    }

    /*@Tag(name = "User")
    @Operation(summary = "온보딩 시 입력받은 아기, 지역정보 저장", description = "dateFormat : yyyy-mm-dd, type : PREGNANCY, PARENTING (대문자로 작성), city : 시, 도, district : 구, 군")
    @PostMapping("/register/info/v2")*/
    public ResponseEntity<ResponseDTO> insertV2(@RequestBody UserRegisterDTOV2 dto, HttpServletRequest httpServletRequest) {

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        UserEntityV2 user = userServiceV2.findByUserId(userId);

        SiggAreas area = areaService.validateArea(dto.getCity(), dto.getDistrict());

        userServiceV2.updateArea(userId, area);
        userServiceV2.updateUserInfo(userId, dto);

        return new ResponseEntity<>(ResponseDTO.of("정보 등록완료.") , HttpStatus.OK);
    }
}
