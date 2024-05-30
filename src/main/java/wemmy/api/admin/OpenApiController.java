package wemmy.api.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import wemmy.service.area.AreaService;
import wemmy.service.area.openApi.OpenApiSercvice;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/admin")
public class OpenApiController {

    private final OpenApiSercvice openApiSercvice;
    private final AreaService areaService;

    @GetMapping("/get/openApi")
    public ResponseEntity<String> getOpenApi() {
        // Open Api에서 데이터 가져오기.
        List<OpenApiRespDTO> result = openApiSercvice.getOpenApi();

        // 가져온 데이터 DB에 삽입.
        areaService.saveArea(result);

        return ResponseEntity.ok("지역정보 저장 완료.");
    }
}
