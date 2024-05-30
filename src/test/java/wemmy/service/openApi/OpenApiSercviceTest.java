package wemmy.service.openApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import wemmy.service.area.AreaService;
import wemmy.service.area.openApi.OpenApiSercvice;

import java.util.List;

@SpringBootTest
class OpenApiSercviceTest {

    @Autowired
    private OpenApiSercvice openApiSercvice;

    @Autowired
    private AreaService areaService;

    @Test
    void test() {
        // 오픈 데이터 정보 가져오기
        List<OpenApiRespDTO> findData = openApiSercvice.getOpenApi();

        // 저장하기.
        areaService.saveArea(findData);
    }
}