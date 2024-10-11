package wemmy.service.area;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.repository.area.RegionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AreaServiceTest {

    @Autowired
    private AreaService areaService;

    @Autowired
    RegionRepository regionRepository;
    @Test
    void validateRegionCode() {

        /*String sidoCode = "11";
        String siggCode = "545";

        Optional<Regions> region = regionRepository.findByRegionCd(sidoCode + siggCode + "00000");

        System.out.println(region.get().getRegion_cd());*/
    }
}