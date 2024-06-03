package wemmy.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.dto.area.openApi.OpenApiRespDTO;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaSaveService {

    private final AreaService areaService;

    public void saveSigg(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String regionCd = data.getRegion_cd();
            String sidoCd = data.getSido_cd();
            String siggCd = data.getSigg_cd();
            String umdCd = data.getUmd_cd();
            String locallowNm = data.getLocallow_nm();

            // 자치구, 군이 없을경우 추가.
            if (umdCd.equals("000") && !areaService.validateSiggCode(siggCd).isPresent()) {
                SidoAreas findResult = areaService.findBySidoCode(sidoCd);

                SiggAreas siggData = SiggAreas.builder()
                        .adm_code(siggCd)
                        .name(locallowNm)
                        .sido_id(findResult)
                        .build();

                areaService.saveSigg(siggData);
            }
        }
    }

    public void saveUmd(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String siggCd = data.getSigg_cd();
            String umdCd = data.getUmd_cd();
            String locallowNm = data.getLocallow_nm();

            SiggAreas findResult = areaService.findBySiggCode(siggCd);

            if (!areaService.validateUmdCodeAndSigg(umdCd, findResult).isPresent()) {
                System.out.println("umd code : " + umdCd);

                UmdAreas umdData = UmdAreas.builder()
                        .adm_code(umdCd)
                        .name(locallowNm)
                        .sigg_id(findResult)
                        .build();
                System.out.println("umd code : " + umdCd + "  " + siggCd);
                areaService.saveUmd(umdData);
            }
        }
    }

    public void saveRegion(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String regionCd = data.getRegion_cd();
            String sidoCd = data.getSido_cd();
            String siggCd = data.getSigg_cd();
            String umdCd = data.getUmd_cd();

            SiggAreas siggAreas = areaService.findBySiggCode(siggCd);

            if (!areaService.findByRegionCode(regionCd).isPresent()) {
                Regions regionData = Regions.builder()
                        .region_cd(regionCd)
                        .sido_id(areaService.findBySidoCode(sidoCd))
                        .sigg_id(areaService.findBySiggCode(siggCd))
                        .umd_id(areaService.findByUmdCodeAndSigg(umdCd, siggAreas))
                        .build();

                areaService.saveRegion(regionData);
            }
        }
    }
}
