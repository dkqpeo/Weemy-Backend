package wemmy.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.area.RegionRepository;
import wemmy.repository.area.SidoAreaRepository;
import wemmy.repository.area.SiguAreaRepository;
import wemmy.repository.area.UmdAreaRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AreaService {

    private final RegionRepository regionRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiguAreaRepository siguAreaRepository;
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

    public Regions getRegionBySiguCode(SiguAreas siguAreas) {
        String sidoCode = siguAreas.getSido_id().getAdm_code();
        String siguCode = siguAreas.getAdm_code();

        log.info("sido code : " +sidoCode);
        log.info("sigu code : " + siguCode);

        regionRepository.findByRegionCd("1154500000");

        Regions region = regionRepository.findByRegionCd(sidoCode + siguCode + "00000")
                .orElseThrow(() -> new ControllerException(ErrorCode.REGION_NOT_EXISTS));
        return region;
    }

    public void saveSido(SidoAreas data) {
        sidoAreaRepository.save(data);
    }

    public void saveSigu(SiguAreas data) {
        siguAreaRepository.save(data);
    }

    public void saveUmd(UmdAreas data) {
        umdAreaRepository.save(data);
    }

    // sido table에 입력받은 시, 구가 존재하는지 확인.
    public SiguAreas validateArea(String city, String district) {
        SidoAreas sido = findBySidoName(city);
        SiguAreas sigu = findBySiguNameAndSidoId(district, sido);

        return sigu;
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
    public SiguAreas findBySiguNameAndSidoId(String name, SidoAreas sidoId) {
        log.info("district : " + name);
        SiguAreas siguArea = siguAreaRepository.findByNameAndSido_id(name, sidoId)
                .orElseThrow(() -> new ControllerException(ErrorCode.DISTRICT_NOT_EXISTS));
        return siguArea;
    }

    public SiguAreas findBySiguCode(String code) {
        SiguAreas siguArea = siguAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.DISTRICT_NOT_EXISTS));
        return siguArea;
    }

    public Optional<SiguAreas> validateSiguCode(String sidoCode, String siguCode) {
        SidoAreas sidoArea = findBySidoCode(sidoCode);
        return siguAreaRepository.findBySidoAndAdm_code(sidoArea, siguCode);
    }

    // 읍면동 조회
    public UmdAreas findByUmdCode(String code) {
        UmdAreas umdArea = umdAreaRepository.findByAdm_code(code)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));
        return umdArea;
    }

    public Optional<UmdAreas> validateUmdCodeAndSigu(String code, SiguAreas siguArea) {
        Optional<UmdAreas> umdArea = umdAreaRepository.findByAdm_codeAndSigu_id(code, siguArea);
        return umdArea;
    }

    public UmdAreas findByUmdCodeAndSigu(String code, SiguAreas siguArea) {
        UmdAreas umdArea = umdAreaRepository.findByAdm_codeAndSigu_id(code, siguArea)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));
        return umdArea;
    }

    public UmdAreas findByUmdAreaByName(String name) {
        UmdAreas umdAreas = umdAreaRepository.findByName(name)
                .orElseThrow(() -> new ControllerException(ErrorCode.UMD_NOT_EXISTS));

        return umdAreas;
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
