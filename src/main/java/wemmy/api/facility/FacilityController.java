package wemmy.api.facility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.dto.facility.FacilityDTO;
import wemmy.dto.welfare.program.ProgramRegisterDTO;
import wemmy.service.facility.FacilityService;

import java.util.List;

@Tag(name = "Facility", description = "지도 복시시설 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/facility")
public class FacilityController {

    private final FacilityService facilityService;

    @Tag(name = "Facility")
    @Operation(summary = "위도, 경도, 범위로 복지시설 타이틀 조회 API", description = "위도, 경도, 범위로 복지시설 타이틀 리스트 조회.")
    @GetMapping("/map/title/{latitude}/{longitude}/{redius}")
    public ResponseEntity<List<FacilityDTO.titleResponse>> getTitleList(@PathVariable("latitude") double latitude,
                                     @PathVariable("longitude") double longitude,
                                     @PathVariable("redius") int redius,
                                     HttpServletRequest httpServletRequest) {

        List<FacilityDTO.titleResponse> response = facilityService.getFacilitiesTitleWithinRadius(longitude, latitude, redius);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Tag(name = "Facility")
    @Operation(summary = "위도, 경도, 범위로 복지시설 조회 API", description = "위도, 경도, 범위로 복지시설 리스트 조회.")
    @GetMapping("/map/{latitude}/{longitude}/{redius}")
    public ResponseEntity<List<FacilityDTO.response>> getList(@PathVariable("latitude") double latitude,
                                     @PathVariable("longitude") double longitude,
                                     @PathVariable("redius") int redius,
                                     HttpServletRequest httpServletRequest) {

        List<FacilityDTO.response> response = facilityService.getFacilitiesWithinRadius(longitude, latitude, redius);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
