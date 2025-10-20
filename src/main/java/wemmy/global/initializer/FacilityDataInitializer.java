package wemmy.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wemmy.dto.facility.FacilitySaveDTO;
import wemmy.service.facility.FacilitySaveService;

import java.util.Arrays;
import java.util.List;

/**
 * 애플리케이션 시작 시 resources/facilities/ 디렉토리의 JSON 파일들을 읽어서
 * 시설 정보를 DB에 저장합니다.
 *
 * application.yml에서 facility.data.init.enabled=true로 설정해야 활성화됩니다.
 * 기본값은 false (비활성화)입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = "facility.data.init.enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class FacilityDataInitializer implements CommandLineRunner {

    private final FacilitySaveService facilitySaveService;

    // facilities 디렉토리의 JSON 파일 목록
    private static final List<String> FACILITY_FILES = Arrays.asList(
            "facilities/childcare_facilities.json",
            "facilities/cultural_facilities.json",
            "facilities/medical_facilities.json",
            "facilities/postpartum_facilities.json"
    );

    @Override
    public void run(String... args) {
        try {
            log.info("시설 데이터 초기화를 시작합니다...");
            initializeFacilityData();
            log.info("시설 데이터 초기화가 완료되었습니다.");

        } catch (Exception e) {
            log.error("시설 데이터 초기화 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public void initializeFacilityData() {
        int totalSaved = 0;
        int totalFailed = 0;

        for (String fileName : FACILITY_FILES) {
            try {
                log.info("파일 처리 중: {}", fileName);

                // 1. JSON 파일 파싱
                List<FacilitySaveDTO> facilityList = facilitySaveService.facilitySave(fileName);

                if (facilityList == null || facilityList.isEmpty()) {
                    log.warn("파일에 데이터가 없습니다: {}", fileName);
                    continue;
                }

                // 2. 파싱된 데이터 저장
                facilitySaveService.facilityParseAndSave(facilityList);

                totalSaved += facilityList.size();
                log.info("파일 처리 완료: {} ({}개 저장)", fileName, facilityList.size());

            } catch (Exception e) {
                totalFailed++;
                log.error("파일 처리 중 오류 발생: {}", fileName, e);
            }
        }

        log.info("===== 시설 데이터 저장 완료 =====");
        log.info("총 저장: {}개", totalSaved);
        log.info("실패한 파일: {}개", totalFailed);
    }
}
