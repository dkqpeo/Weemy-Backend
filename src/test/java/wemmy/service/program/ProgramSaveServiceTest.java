package wemmy.service.program;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntity;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.dto.program.ProgramSaveDTO;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.service.area.AreaService;
import wemmy.service.baby.BabyService;
import wemmy.service.benefit.BenefitSaveService;
import wemmy.service.benefit.BenefitService;
import wemmy.service.scrap.ScrapService;
import wemmy.service.user.UserService;
import wemmy.service.welfare.ProgramSaveService;

import java.util.List;

import static wemmy.domain.baby.constant.BabyType.PREGNANCY;

@SpringBootTest
class ProgramSaveServiceTest {

    @Autowired
    private ProgramSaveService programSaveService;

    @Test
    void programSave() {
        // resource/benefit에 있는 json파일 파싱
        try {
            //List<ProgramSaveDTO> programSaveDTO = programSaveService.programSave("/program/program_2024-09-24_result (1).json");

            //programSaveService.programParseAndSave(programSaveDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}