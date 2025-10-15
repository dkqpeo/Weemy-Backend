package wemmy.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.dto.area.openApi.OpenApiRespDTO;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaSaveService {

    private final AreaService areaService;

    /**
     * 군, 구 데이터 저장
     * @param list
     */
    public void saveSigu(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String regionCd = data.getRegion_cd();
            String sidoCd = data.getSido_cd();
            String siguCd = data.getSigu_cd();
            String umdCd = data.getUmd_cd();
            String locallowNm = data.getLocallow_nm();

            // 자치구, 군이 없을경우 추가.
            if (umdCd.equals("000") && !areaService.validateSiguCode(sidoCd, siguCd).isPresent()) {
                SidoAreas findResult = areaService.findBySidoCode(sidoCd);

                SiguAreas siguData = SiguAreas.builder()
                        .adm_code(siguCd)
                        .name(locallowNm)
                        .sido_id(findResult)
                        .build();

                areaService.saveSigu(siguData);
            }
        }
    }

    /**
     * 읍, 면, 동 저장
     * @param list
     */
    public void saveUmd(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String siguCd = data.getSigu_cd();
            String umdCd = data.getUmd_cd();
            String locallowNm = data.getLocallow_nm();

            SiguAreas findResult = areaService.findBySiguCode(siguCd);

            if (!areaService.validateUmdCodeAndSigu(umdCd, findResult).isPresent()) {
                log.debug("umd code: {}", umdCd);

                UmdAreas umdData = UmdAreas.builder()
                        .adm_code(umdCd)
                        .name(locallowNm)
                        .sigu_id(findResult)
                        .build();
                log.debug("umd code: {} sigu code: {}", umdCd, siguCd);
                areaService.saveUmd(umdData);
            }
        }
    }

    public void saveRegion(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String regionCd = data.getRegion_cd();
            String sidoCd = data.getSido_cd();
            String siguCd = data.getSigu_cd();
            String umdCd = data.getUmd_cd();

            SiguAreas siguAreas = areaService.findBySiguCode(siguCd);

            if (!areaService.findByRegionCode(regionCd).isPresent()) {
                Regions regionData = Regions.builder()
                        .region_cd(regionCd)
                        .sido_id(areaService.findBySidoCode(sidoCd))
                        .sigu_id(areaService.findBySiguCode(siguCd))
                        .umd_id(areaService.findByUmdCodeAndSigu(umdCd, siguAreas))
                        .build();

                areaService.saveRegion(regionData);
            }
        }
    }
}
