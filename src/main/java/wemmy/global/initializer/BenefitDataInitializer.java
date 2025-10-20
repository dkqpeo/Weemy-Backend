package wemmy.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wemmy.dto.welfare.benefit.BenefitSaveDTO;
import wemmy.service.benefit.BenefitSaveService;
import wemmy.service.benefit.BenefitServiceV2;

import java.util.Arrays;
import java.util.List;

/**
 * 애플리케이션 시작 시 resources/benefit/ 디렉토리의 JSON 파일들을 읽어서
 * 복지 정보를 DB에 저장합니다.
 *
 * application.yml에서 benefit.data.init.enabled=true로 설정해야 활성화됩니다.
 * 기본값은 false (비활성화)입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = "benefit.data.init.enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class BenefitDataInitializer implements CommandLineRunner {

    private final BenefitSaveService benefitSaveService;
    private final BenefitServiceV2 benefitServiceV2;

    // benefit 디렉토리의 JSON 파일 목록
    private static final List<String> BENEFIT_FILES = Arrays.asList(
            "benefit/db_2024-08-12_result (1).json",
            "benefit/ddm_2024-08-29_result.json",
            "benefit/dj_2024-07-10_result.json",
            "benefit/ep_2024-08-06_result.json",
            "benefit/ga_2024-07-12_result.json",
            "benefit/gb_2024-08-12_result (1).json",
            "benefit/gd_2024-08-05_result (2).json",
            "benefit/geumcheon_2024-07-11_result.json",
            "benefit/gj_2024-07-09_result.json",
            "benefit/gn_2024-07-15_result.json",
            "benefit/gn_2024-08-05_result.json",
            "benefit/gr_2024-07-12_result.json",
            "benefit/gs_2024-07-12_result.json",
            "benefit/jg_2024-08-30_result.json",
            "benefit/jn_2024-08-28_result.json",
            "benefit/jr_2024-08-30_result.json",
            "benefit/mp_2024-08-05_result.json",
            "benefit/nw_2024-08-12_result.json",
            "benefit/sb_2024-08-12_result.json",
            "benefit/sd_2024-08-29_result.json",
            "benefit/sdm_2024-08-06_result (2).json",
            "benefit/sh_2024-07-15_result.json",
            "benefit/sp_2024-08-05_result (3).json",
            "benefit/yc_2024-08-05_result (2).json",
            "benefit/ydp_2024-07-08_result.json",
            "benefit/ydp_2024-07-09_result.json",
            "benefit/ydp_2024-07-10_result.json",
            "benefit/ys_2024-08-29_result.json"
    );

    @Override
    public void run(String... args) {
        try {
            log.info("복지 데이터 초기화를 시작합니다...");
            initializeBenefitData();
            log.info("복지 데이터 초기화가 완료되었습니다.");

        } catch (Exception e) {
            log.error("복지 데이터 초기화 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public void initializeBenefitData() {
        int totalSaved = 0;
        int totalFailed = 0;

        for (String fileName : BENEFIT_FILES) {
            try {
                log.info("파일 처리 중: {}", fileName);

                // 1. JSON 파일 파싱
                List<BenefitSaveDTO> benefitList = benefitSaveService.benefitSave(fileName);

                if (benefitList == null || benefitList.isEmpty()) {
                    log.warn("파일에 데이터가 없습니다: {}", fileName);
                    continue;
                }

                // 2. 파싱된 데이터 저장
                benefitServiceV2.benefitParseAndSave(benefitList);

                totalSaved += benefitList.size();
                log.info("파일 처리 완료: {} ({}개 저장)", fileName, benefitList.size());

            } catch (Exception e) {
                totalFailed++;
                log.error("파일 처리 중 오류 발생: {}", fileName, e);
            }
        }

        log.info("===== 복지 데이터 저장 완료 =====");
        log.info("총 저장: {}개", totalSaved);
        log.info("실패한 파일: {}개", totalFailed);
    }
}
