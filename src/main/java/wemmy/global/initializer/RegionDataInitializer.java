package wemmy.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import wemmy.repository.area.RegionRepository;
import wemmy.repository.area.SidoAreaRepository;
import wemmy.repository.area.SiguAreaRepository;
import wemmy.repository.area.UmdAreaRepository;
import wemmy.service.area.openApi.OpenApiSercvice;

import java.util.List;

/**
 * 애플리케이션 시작 시 지역 정보를 Open API로부터 가져와서 DB에 초기화합니다.
 * 이미 데이터가 존재하는 경우 초기화를 건너뜁니다.
 *
 * application.yml에서 region.data.init.enabled=true로 설정해야 활성화됩니다.
 * 기본값은 false (비활성화)입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = "region.data.init.enabled",
    havingValue = "true",
    matchIfMissing = false  // 설정이 없으면 비활성화
)
public class RegionDataInitializer implements CommandLineRunner {

    private final OpenApiSercvice openApiService;
    private final RegionRepository regionRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiguAreaRepository siguAreaRepository;
    private final UmdAreaRepository umdAreaRepository;

    @Override
    public void run(String... args) {
        try {
            // 이미 지역 데이터가 존재하면 초기화 건너뛰기
            long count = regionRepository.count();
            if (count > 0) {
                log.info("지역 데이터가 이미 존재합니다. (총 {}개) 초기화를 건너뜁니다.", count);
                return;
            }

            log.info("지역 데이터 초기화를 시작합니다...");
            initializeRegionData();
            log.info("지역 데이터 초기화가 완료되었습니다.");

        } catch (Exception e) {
            log.error("지역 데이터 초기화 중 오류가 발생했습니다.", e);
            // 애플리케이션 시작은 계속 진행 (초기화 실패가 앱 전체를 중단시키지 않도록)
        }
    }

    @Transactional
    public void initializeRegionData() {
        // Open API로부터 지역 데이터 가져오기
        List<OpenApiRespDTO> regionDataList = openApiService.getOpenApi();
        log.info("Open API로부터 {}개의 지역 데이터를 가져왔습니다.", regionDataList.size());

        // 1. Sido (시도) 저장
        saveSidoAreas(regionDataList);

        // 2. Sigu (시군구) 저장
        saveSiguAreas(regionDataList);

        // 3. Umd (읍면동) 저장
        saveUmdAreas(regionDataList);

        // 4. Region 저장
        saveRegions(regionDataList);

        log.info("지역 데이터 초기화가 완료되었습니다.");
    }

    /**
     * 시도(Sido) 데이터 저장 - 서울특별시만 저장
     */
    private void saveSidoAreas(List<OpenApiRespDTO> dataList) {
        int savedCount = 0;

        // 1. 서울특별시 저장
        for (OpenApiRespDTO data : dataList) {
            try {
                String sidoCd = data.getSido_cd();
                String locallowNm = data.getLocallow_nm();

                if (sidoCd == null || sidoCd.isEmpty()) {
                    continue;
                }

                // 서울특별시만 저장 (locallowNm이 "서울특별시"로 시작하는 경우)
                if (locallowNm == null || !locallowNm.startsWith("서울특별시")) {
                    continue;
                }

                // 이미 존재하는지 확인
                if (sidoAreaRepository.findByAdm_code(sidoCd).isPresent()) {
                    continue;
                }

                SidoAreas sidoArea = SidoAreas.builder()
                        .adm_code(sidoCd)
                        .name("서울특별시")
                        .build();

                sidoAreaRepository.save(sidoArea);
                savedCount++;

            } catch (Exception e) {
                log.warn("Sido 데이터 저장 중 오류: {}", data.getLocallow_nm(), e);
            }
        }

        log.info("Sido 데이터 저장 완료: {}개", savedCount);
    }

    /**
     * 시군구(Sigu) 데이터 저장 - umd_cd가 "000"인 자치구/군만 저장
     */
    private void saveSiguAreas(List<OpenApiRespDTO> dataList) {
        int savedCount = 0;

        for (OpenApiRespDTO data : dataList) {
            try {
                String sidoCd = data.getSido_cd();
                String siguCd = data.getSigu_cd();
                String umdCd = data.getUmd_cd();
                String locallowNm = data.getLocallow_nm();

                // umd_cd가 "000"인 경우만 처리 (자치구/군 레벨)
                if (!"000".equals(umdCd)) {
                    continue;
                }

                if (siguCd == null || siguCd.isEmpty()) {
                    continue;
                }

                // Sido 조회
                SidoAreas sidoArea = sidoAreaRepository.findByAdm_code(sidoCd).orElse(null);
                if (sidoArea == null) {
                    continue;
                }

                // 이미 존재하는지 확인
                if (siguAreaRepository.findBySidoAndAdm_code(sidoArea, siguCd).isPresent()) {
                    continue;
                }

                SiguAreas siguArea = SiguAreas.builder()
                        .adm_code(siguCd)
                        .name(locallowNm)
                        .sido_id(sidoArea)
                        .build();

                siguAreaRepository.save(siguArea);
                savedCount++;

            } catch (Exception e) {
                log.warn("Sigu 데이터 저장 중 오류: {}", data.getLocallow_nm(), e);
            }
        }

        log.info("Sigu 데이터 저장 완료: {}개", savedCount);
    }

    /**
     * 읍면동(Umd) 데이터 저장
     */
    private void saveUmdAreas(List<OpenApiRespDTO> dataList) {
        int savedCount = 0;

        for (OpenApiRespDTO data : dataList) {
            try {
                String siguCd = data.getSigu_cd();
                String umdCd = data.getUmd_cd();
                String locallowNm = data.getLocallow_nm();

                if (umdCd == null || umdCd.isEmpty() || siguCd == null || siguCd.isEmpty()) {
                    continue;
                }

                // Sigu 조회
                SiguAreas siguArea = siguAreaRepository.findByAdm_code(siguCd).orElse(null);
                if (siguArea == null) {
                    continue;
                }

                // 이미 존재하는지 확인
                if (umdAreaRepository.findByAdm_codeAndSigu_id(umdCd, siguArea).isPresent()) {
                    continue;
                }

                UmdAreas umdArea = UmdAreas.builder()
                        .adm_code(umdCd)
                        .name(locallowNm)
                        .sigu_id(siguArea)
                        .build();

                umdAreaRepository.save(umdArea);
                savedCount++;

            } catch (Exception e) {
                log.warn("Umd 데이터 저장 중 오류: {}", data.getLocallow_nm(), e);
            }
        }

        log.info("Umd 데이터 저장 완료: {}개", savedCount);
    }

    /**
     * Region 데이터 저장
     */
    private void saveRegions(List<OpenApiRespDTO> dataList) {
        int savedCount = 0;

        for (OpenApiRespDTO data : dataList) {
            try {
                String regionCd = data.getRegion_cd();
                String sidoCd = data.getSido_cd();
                String siguCd = data.getSigu_cd();
                String umdCd = data.getUmd_cd();

                if (regionCd == null || regionCd.isEmpty()) {
                    continue;
                }

                // 이미 존재하는지 확인
                if (regionRepository.findByRegionCd(regionCd).isPresent()) {
                    continue;
                }

                // Sido, Sigu, Umd 조회
                SidoAreas sidoArea = sidoAreaRepository.findByAdm_code(sidoCd).orElse(null);
                SiguAreas siguArea = siguAreaRepository.findByAdm_code(siguCd).orElse(null);
                UmdAreas umdArea = null;

                if (siguArea != null) {
                    umdArea = umdAreaRepository.findByAdm_codeAndSigu_id(umdCd, siguArea).orElse(null);
                }

                Regions region = Regions.builder()
                        .region_cd(regionCd)
                        .sido_id(sidoArea)
                        .sigu_id(siguArea)
                        .umd_id(umdArea)
                        .build();

                regionRepository.save(region);
                savedCount++;

            } catch (Exception e) {
                log.warn("Region 데이터 저장 중 오류: {}", data.getLocallow_nm(), e);
            }
        }

        log.info("Region 데이터 저장 완료: {}개", savedCount);
    }
}
