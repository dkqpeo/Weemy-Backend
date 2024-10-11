package wemmy.service.openApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.city.SidoAreas;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import wemmy.service.area.AreaSaveService;
import wemmy.service.area.AreaService;
import wemmy.service.area.openApi.OpenApiSercvice;

import java.util.List;

@SpringBootTest
class OpenApiSercviceTest {

    @Autowired
    private OpenApiSercvice openApiSercvice;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaSaveService areaSaveService;

    @Test
    void 서울시_저장() {
        /*SidoAreas sido = SidoAreas.builder()
                .name("서울특별시")
                .adm_code("11")
                .build();

        areaService.saveSido(sido);*/
    }

    @Test
    void 정부_저장() {
        /*SidoAreas sido = SidoAreas.builder()
                .name("정부")
                .adm_code("90")
                .build();

        areaService.saveSido(sido);*/
    }
    @Test
    void test() {
        // 오픈 데이터 정보 가져오기
        //List<OpenApiRespDTO> findData = openApiSercvice.getOpenApi();

        // 군, 구 데이터 저장
        //areaSaveService.saveSigg(findData);

        // 읍, 면, 동 데이터 저장
        //areaSaveService.saveUmd(findData);

        // region code 저장
        //areaSaveService.saveRegion(findData);
    }
}