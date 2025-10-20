package wemmy.service.facility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.facility.Facility;
import wemmy.dto.facility.FacilityDTO;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.facility.FacilityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacilityServiceTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityService facilityService;

    private SidoAreas sidoArea;
    private SiguAreas siguArea;
    private Facility childcareFacility;
    private Facility culturalFacility;
    private Facility medicalFacility;
    private Facility postpartumFacility;

    @BeforeEach
    void setUp() {
        // 시도 (서울특별시)
        sidoArea = SidoAreas.builder()
                .id(11L)
                .adm_code("11")
                .name("서울특별시")
                .build();

        // 시구 (강남구)
        siguArea = SiguAreas.builder()
                .id(11680L)
                .adm_code("001")
                .sido_id(sidoArea)
                .name("강남구")
                .build();

        // 육아시설
        childcareFacility = Facility.builder()
                .id(1L)
                .facilityName("행복 어린이집")
                .address("서울특별시 강남구 테헤란로 123")
                .tel("02-1234-5678")
                .longitude(127.0276)
                .latitude(37.4979)
                .categoryMain("육아시설")
                .categorySub("어린이집")
                .operatingHours("09:00-18:00")
                .district(siguArea)
                .build();

        // 행정시설
        culturalFacility = Facility.builder()
                .id(2L)
                .facilityName("강남구청")
                .address("서울특별시 강남구 학동로 426")
                .tel("02-2222-3333")
                .longitude(127.0475)
                .latitude(37.5172)
                .categoryMain("행정시설")
                .categorySub("구청")
                .operatingHours("09:00-18:00")
                .district(siguArea)
                .build();

        // 의료시설
        medicalFacility = Facility.builder()
                .id(3L)
                .facilityName("서울대병원")
                .address("서울특별시 강남구 논현로 567")
                .tel("02-3333-4444")
                .longitude(127.0369)
                .latitude(37.5062)
                .categoryMain("의료시설")
                .categorySub("종합병원")
                .operatingHours("24시간")
                .district(siguArea)
                .build();

        // 산후조리원
        postpartumFacility = Facility.builder()
                .id(4L)
                .facilityName("행복 산후조리원")
                .address("서울특별시 강남구 봉은사로 789")
                .tel("02-4444-5555")
                .longitude(127.0403)
                .latitude(37.5115)
                .categoryMain("산후조리원")
                .categorySub("산후조리원")
                .operatingHours("24시간")
                .district(siguArea)
                .build();
    }

    @Test
    @DisplayName("시설 저장 성공")
    void facilitySave_Success() {
        // given
        when(facilityRepository.save(any(Facility.class))).thenReturn(childcareFacility);

        // when
        facilityService.facilitySave(childcareFacility);

        // then
        verify(facilityRepository, times(1)).save(childcareFacility);
    }

    @Test
    @DisplayName("반경 내 시설 조회 성공 - 4개 카테고리")
    void getFacilitiesWithinRadius_Success() {
        // given
        double longitude = 127.0276;
        double latitude = 37.4979;
        double radius = 5000.0;

        List<Facility> facilities = List.of(
                childcareFacility,
                culturalFacility,
                medicalFacility,
                postpartumFacility
        );

        when(facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius))
                .thenReturn(facilities);

        // when
        FacilityDTO.response response = facilityService.getFacilitiesWithinRadius(longitude, latitude, radius);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getFacilitiesData()).hasSize(4);

        // 육아시설 검증
        FacilityDTO.detailResponse childcareResponse = response.getFacilitiesData().get(0);
        assertThat(childcareResponse.getCategory()).isEqualTo("보육시설");
        assertThat(childcareResponse.getData()).hasSize(1);
        assertThat(childcareResponse.getData().get(0).getFacilityName()).isEqualTo("행복 어린이집");

        // 행정시설 검증
        FacilityDTO.detailResponse culturalResponse = response.getFacilitiesData().get(1);
        assertThat(culturalResponse.getCategory()).isEqualTo("공공시설");
        assertThat(culturalResponse.getData()).hasSize(1);
        assertThat(culturalResponse.getData().get(0).getFacilityName()).isEqualTo("강남구청");

        // 의료시설 검증
        FacilityDTO.detailResponse medicalResponse = response.getFacilitiesData().get(2);
        assertThat(medicalResponse.getCategory()).isEqualTo("의료시설");
        assertThat(medicalResponse.getData()).hasSize(1);
        assertThat(medicalResponse.getData().get(0).getFacilityName()).isEqualTo("서울대병원");

        // 산후조리원 검증
        FacilityDTO.detailResponse postpartumResponse = response.getFacilitiesData().get(3);
        assertThat(postpartumResponse.getCategory()).isEqualTo("산후조리원");
        assertThat(postpartumResponse.getData()).hasSize(1);
        assertThat(postpartumResponse.getData().get(0).getFacilityName()).isEqualTo("행복 산후조리원");

        verify(facilityRepository, times(1)).findFacilitiesWithinRadius(longitude, latitude, radius);
    }

    @Test
    @DisplayName("반경 내 시설 조회 - 빈 리스트")
    void getFacilitiesWithinRadius_EmptyList() {
        // given
        double longitude = 127.0276;
        double latitude = 37.4979;
        double radius = 100.0;

        when(facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius))
                .thenReturn(new ArrayList<>());

        // when
        FacilityDTO.response response = facilityService.getFacilitiesWithinRadius(longitude, latitude, radius);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getFacilitiesData()).hasSize(4);
        // 모든 카테고리가 빈 리스트여야 함
        response.getFacilitiesData().forEach(detailResponse ->
                assertThat(detailResponse.getData()).isEmpty()
        );

        verify(facilityRepository, times(1)).findFacilitiesWithinRadius(longitude, latitude, radius);
    }

    @Test
    @DisplayName("반경 내 시설 제목 조회 성공 - 4개 카테고리")
    void getFacilitiesTitleWithinRadius_Success() {
        // given
        double longitude = 127.0276;
        double latitude = 37.4979;
        double radius = 5000.0;

        List<Facility> facilities = List.of(
                childcareFacility,
                culturalFacility,
                medicalFacility,
                postpartumFacility
        );

        when(facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius))
                .thenReturn(facilities);

        // when
        List<FacilityDTO.titleResponse> response = facilityService.getFacilitiesTitleWithinRadius(longitude, latitude, radius);

        // then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(4);

        // 육아시설 검증
        FacilityDTO.titleResponse childcareResponse = response.get(0);
        assertThat(childcareResponse.getCategory()).isEqualTo("보육시설");
        assertThat(childcareResponse.getData()).hasSize(1);
        assertThat(childcareResponse.getData().get(0).getFacilityName()).isEqualTo("행복 어린이집");

        // 행정시설 검증
        FacilityDTO.titleResponse culturalResponse = response.get(1);
        assertThat(culturalResponse.getCategory()).isEqualTo("공공시설");
        assertThat(culturalResponse.getData()).hasSize(1);
        assertThat(culturalResponse.getData().get(0).getFacilityName()).isEqualTo("강남구청");

        // 의료시설 검증
        FacilityDTO.titleResponse medicalResponse = response.get(2);
        assertThat(medicalResponse.getCategory()).isEqualTo("의료시설");
        assertThat(medicalResponse.getData()).hasSize(1);
        assertThat(medicalResponse.getData().get(0).getFacilityName()).isEqualTo("서울대병원");

        // 산후조리원 검증
        FacilityDTO.titleResponse postpartumResponse = response.get(3);
        assertThat(postpartumResponse.getCategory()).isEqualTo("산후조리원");
        assertThat(postpartumResponse.getData()).hasSize(1);
        assertThat(postpartumResponse.getData().get(0).getFacilityName()).isEqualTo("행복 산후조리원");

        verify(facilityRepository, times(1)).findFacilitiesWithinRadius(longitude, latitude, radius);
    }

    @Test
    @DisplayName("반경 내 시설 제목 조회 - 빈 리스트")
    void getFacilitiesTitleWithinRadius_EmptyList() {
        // given
        double longitude = 127.0276;
        double latitude = 37.4979;
        double radius = 100.0;

        when(facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius))
                .thenReturn(new ArrayList<>());

        // when
        List<FacilityDTO.titleResponse> response = facilityService.getFacilitiesTitleWithinRadius(longitude, latitude, radius);

        // then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(4);
        // 모든 카테고리가 빈 리스트여야 함
        response.forEach(titleResponse ->
                assertThat(titleResponse.getData()).isEmpty()
        );

        verify(facilityRepository, times(1)).findFacilitiesWithinRadius(longitude, latitude, radius);
    }

    @Test
    @DisplayName("시설 상세 조회 성공")
    void getDetail_Success() {
        // given
        Long facilityId = 1L;
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(childcareFacility));

        // when
        FacilityDTO.facilityDetail result = facilityService.getDetail(facilityId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFacilityId()).isEqualTo(1L);
        assertThat(result.getFacilityName()).isEqualTo("행복 어린이집");
        assertThat(result.getAddress()).isEqualTo("서울특별시 강남구 테헤란로 123");
        assertThat(result.getTel()).isEqualTo("02-1234-5678");
        assertThat(result.getCategorySub()).isEqualTo("어린이집");
        assertThat(result.getCity()).isEqualTo("서울특별시");
        assertThat(result.getDistrict()).isEqualTo("강남구");
        assertThat(result.getOperatingHours()).isEqualTo("09:00-18:00");
        assertThat(result.getLongitude()).isEqualTo(127.0276);
        assertThat(result.getLatitude()).isEqualTo(37.4979);

        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    @DisplayName("시설 상세 조회 실패 - 존재하지 않는 시설")
    void getDetail_NotFound() {
        // given
        Long facilityId = 999L;
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> facilityService.getDetail(facilityId))
                .isInstanceOf(ControllerException.class);

        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    @DisplayName("ID로 시설 조회 성공")
    void findById_Success() {
        // given
        Long facilityId = 1L;
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(childcareFacility));

        // when
        Facility result = facilityService.findById(facilityId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFacilityName()).isEqualTo("행복 어린이집");
        assertThat(result.getCategoryMain()).isEqualTo("육아시설");

        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    @DisplayName("ID로 시설 조회 실패 - 예외 발생")
    void findById_NotFound() {
        // given
        Long facilityId = 999L;
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> facilityService.findById(facilityId))
                .isInstanceOf(ControllerException.class);

        verify(facilityRepository, times(1)).findById(facilityId);
    }
}
