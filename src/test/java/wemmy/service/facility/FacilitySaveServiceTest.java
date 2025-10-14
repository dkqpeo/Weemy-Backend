package wemmy.service.facility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.dto.facility.FacilitySaveDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FacilitySaveServiceTest {

    @Autowired
    private FacilitySaveService facilitySaveService;
    @Test
    void facilitySave() {
        // List<FacilitySaveDTO> facilitySaveDTO = facilitySaveService.facilitySave("/facilities/childcare_facilities.json");  // 육아시설.
        // List<FacilitySaveDTO> facilitySaveDTO = facilitySaveService.facilitySave("/facilities/cultural_facilities.json");  // 행정시설.
        // List<FacilitySaveDTO> facilitySaveDTO = facilitySaveService.facilitySave("/facilities/medical_facilities.json");  // 의료시설.
        // List<FacilitySaveDTO> facilitySaveDTO = facilitySaveService.facilitySave("/facilities/postpartum_facilities.json");  // 산후조리원.

        // facilitySaveService.facilityParseAndSave(facilitySaveDTO);
    }
}