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
    @GetMapping("/map/title/{latitude}/{longitude}/{radius}")
    public ResponseEntity<List<FacilityDTO.titleResponse>> getTitleList(@PathVariable("latitude") double latitude,
                                     @PathVariable("longitude") double longitude,
                                     @PathVariable("radius") int radius,
                                     HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        List<FacilityDTO.titleResponse> response = facilityService.getFacilitiesTitleWithinRadius(longitude, latitude, radius);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Tag(name = "Facility")
    @Operation(summary = "위도, 경도, 범위로 복지시설 조회 API", description = "위도, 경도, 범위로 복지시설 리스트 조회.")
    @GetMapping("/map/{latitude}/{longitude}/{radius}")
    public ResponseEntity<FacilityDTO.response> getList(@PathVariable("latitude") double latitude,
                                     @PathVariable("longitude") double longitude,
                                     @PathVariable("radius") int radius,
                                     HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        FacilityDTO.response response = facilityService.getFacilitiesWithinRadius(longitude, latitude, radius);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Tag(name = "Facility")
    @Operation(summary = "위도, 경도, 범위로 복지시설 조회 API", description = "위도, 경도, 범위로 복지시설 리스트 조회.")
    @GetMapping("/map/detail/{id}")
    public ResponseEntity<FacilityDTO.facilityDetail> getDetail(@PathVariable("id") Long id,
                                                        HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        FacilityDTO.facilityDetail result = facilityService.getDetail(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
