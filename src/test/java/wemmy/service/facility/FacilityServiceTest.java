package wemmy.service.facility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.dto.facility.FacilityDTO;

import java.util.List;

@SpringBootTest
class FacilityServiceTest {

    @Autowired
    private FacilityService facilityService;

    @Test
    void getFacilitiesWithinRadius() {
        List<FacilityDTO.response> responses = facilityService.getFacilitiesWithinRadius(126.9660613, 37.58837123, 2000);

        System.out.println(responses.size());

    }

}