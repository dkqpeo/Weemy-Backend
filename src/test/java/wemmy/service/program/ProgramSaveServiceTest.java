package wemmy.service.program;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.service.welfare.ProgramSaveService;

@SpringBootTest
class ProgramSaveServiceTest {

    @Autowired
    private ProgramSaveService programSaveService;

    /*@Test
    void programSave() {
        // resource/benefit에 있는 json파일 파싱
        try {
            List<ProgramSaveDTO> programSaveDTO = programSaveService.programSave("/program/program_2024-09-24_result (1).json");

            programSaveService.programParseAndSave(programSaveDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}