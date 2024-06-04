package wemmy.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.area.RegionRepository;
import wemmy.repository.area.SidoAreaRepository;
import wemmy.repository.area.SiggAreaRepository;
import wemmy.repository.area.UmdAreaRepository;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AreaService {

    private final RegionRepository regionRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiggAreaRepository siggAreaRepository;
    private final UmdAreaRepository umdAreaRepository;

    // 시, 군, 구의 전체 지역코드를 저장.
    public void saveRegion(Regions regionData) {
        regionRepository.save(regionData);
    }

    public Regions getRegionById(Long id) {
        Regions region = regionRepository.findById(id)
                .orElseThrow(() -> new ControllerException(ErrorCode.REGION_NOT_EXISTS));
        return region;
    }

    public Regions getRegionBySiggCode(SiggAreas siggAreas) {
        String sidoCode = siggAreas.getSido_id().getAdm_code();
        String siggCode = siggAreas.getAdm_code();
        Regions region = regionRepository.findByRegionCd(sidoCode + siggCode + "00000")
                .orElseThrow(() -> new ControllerException(ErrorCode.REGION_NOT_EXISTS));
        return region;
    }

    public void saveSido(SidoAreas data) {
        sidoAreaRepository.save(data);
    }

    public void saveSigg(SiggAreas data) {
        siggAreaRepository.save(data);
    }

    public void saveUmd(UmdAreas data) {
        umdAreaRepository.save(data);
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

    public Optional<SidoAreas> validateSidoCode(String code) {
        return sidoAreaRepository.findByAdm_code(code);
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

    public Optional<SiggAreas> validateSiggCode(String sidoCode, String siggCode) {
        SidoAreas sidoArea = findBySidoCode(sidoCode);
        return siggAreaRepository.findBySidoAndAdm_code(sidoArea, siggCode);
    }

    // 읍면동 조회
    public UmdAreas findByUmdCode(String code) {
        UmdAreas umdArea = umdAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));
        return umdArea;
    }

    public Optional<UmdAreas> validateUmdCodeAndSigg(String code, SiggAreas siggArea) {
        Optional<UmdAreas> umdArea = umdAreaRepository.findByAdm_codeAndSigg_id(code, siggArea);
        return umdArea;
    }

    public UmdAreas findByUmdCodeAndSigg(String code, SiggAreas siggArea) {
        UmdAreas umdArea = umdAreaRepository.findByAdm_codeAndSigg_id(code, siggArea)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));
        return umdArea;
    }

    // region 조회
    public Regions findByRegionId(Regions regions) {
        Long id = regions.getId();
        Regions region = regionRepository.findById(id)
                .orElseThrow(() -> new ControllerException(ErrorCode.REGION_NOT_EXISTS));
        return region;
    }

    public Optional<Regions> findByRegionCode(String code) {
        Optional<Regions> region = regionRepository.findByRegionCd(code);
        return region;
    }

    public Regions validateRegionCode(String code) {
        Regions region = regionRepository.findByRegionCd(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.REGION_NOT_EXISTS));
        return region;
    }
}
