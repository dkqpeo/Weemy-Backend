package wemmy.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.area.SidoAreaRepository;
import wemmy.repository.area.SiggAreaRepository;
import wemmy.repository.area.UmdAreaRepository;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AreaService {

    private final SidoAreaRepository sidoAreaRepository;
    private final SiggAreaRepository siggAreaRepository;
    private final UmdAreaRepository umdAreaRepository;


    public void saveArea(List<OpenApiRespDTO> list) {

        for (OpenApiRespDTO data : list) {
            String sidoCd = data.getSido_cd();
            String siggCd = data.getSigg_cd();
            String umdCd = data.getUmd_cd();
            String locallowNm = data.getLocallow_nm();

            System.out.println(sidoCd + siggCd + umdCd);

            // 지역시 데이터가 없을경우 추가.
            if(sidoAreaRepository.findByAdm_code(sidoCd).isEmpty()) {
                SidoAreas sidoData = SidoAreas.builder()
                        .adm_code(sidoCd)
                        .name(locallowNm)
                        .build();
                sidoAreaRepository.save(sidoData);
            }

            // 자치구, 군이 없을경우 추가.
            if(siggAreaRepository.findByAdm_code(siggCd).isEmpty()) {
                SidoAreas findResult = findBySidoCode(sidoCd);

                SiggAreas siggData = SiggAreas.builder()
                        .adm_code(siggCd)
                        .name(locallowNm)
                        .sido_id(findResult)
                        .build();
                siggAreaRepository.save(siggData);
            }

            // 읍, 면, 동이 없을경우 추가.
            if(!umdCd.equals("000") || umdAreaRepository.findByAdm_code(umdCd).isEmpty()) {
                SiggAreas findResult = findBySiggCode(siggCd);

                UmdAreas umdData = UmdAreas.builder()
                        .adm_code(umdCd)
                        .name(locallowNm)
                        .sigg_id(findResult)
                        .build();

                umdAreaRepository.save(umdData);
            }
        }
    }

    // sido table에 입력받은 시, 구가 존재하는지 확인.
    public SiggAreas validateArea(String city, String district) {
        SidoAreas sido = findBySidoName(city);
        SiggAreas sigg = findBySiggNameAndSidoId(district, sido);

        return sigg;
    }

    // 지역시 조회
    public SidoAreas findBySidoName(String name) {
        SidoAreas sidoArea = sidoAreaRepository.findByName(name)
                .orElseThrow(() -> new ControllerException(ErrorCode.CITY_NOT_EXISTS));
        return sidoArea;
    }

    public SidoAreas findBySidoCode(String code) {
        SidoAreas sidoArea = sidoAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.CITY_NOT_EXISTS));
        return sidoArea;
    }

    // 지역구 조회
    public SiggAreas findBySiggNameAndSidoId(String name, SidoAreas sidoId) {
        SiggAreas siggArea = siggAreaRepository.findByNameAndSido_id(name, sidoId)
                .orElseThrow(() -> new ControllerException(ErrorCode.DISTRICT_NOT_EXISTS));
        return siggArea;
    }

    public SiggAreas findBySiggCode(String code) {
        SiggAreas siggArea = siggAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.DISTRICT_NOT_EXISTS));
        return siggArea;
    }

    // 읍면동 조회
    public UmdAreas findByUmdCode(String code) {
        UmdAreas umdArea = umdAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));
        return umdArea;
    }
}
