package wemmy.service.facility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.facility.Facility;
import wemmy.dto.facility.FacilityDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.facility.FacilityRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public void facilitySave(Facility facility) {

        facilityRepository.save(facility);
        log.info("FacilityService-facilitySave : 저장 완료.");
    }

    public FacilityDTO.response getFacilitiesWithinRadius(double longitude, double latitude, double radius) {

        List<Facility> facilitiy = facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius);

        List<FacilityDTO.facilityDetail> childcare_facilitieList = new ArrayList<>();       // 육아시설
        List<FacilityDTO.facilityDetail> cultural_facilitieList = new ArrayList<>();        // 행정시설
        List<FacilityDTO.facilityDetail> medical_facilitieList = new ArrayList<>();         // 의료시설
        List<FacilityDTO.facilityDetail> postpartum_facilitieList = new ArrayList<>();      // 산후조리원

        for (Facility facility : facilitiy) {
            FacilityDTO.facilityDetail data = FacilityDTO.facilityDetail.builder()
                    .facilityId(facility.getId())
                    .facilityName(facility.getFacilityName())
                    .address(facility.getAddress())
                    .tel(facility.getTel())
                    .categorySub(facility.getCategorySub())
                    .city(facility.getDistrict().getSido_id().getName())
                    .district(facility.getDistrict().getName())
                    .operatingHours(facility.getOperatingHours())
                    .longitude(facility.getLongitude())
                    .latitude(facility.getLatitude())
                    .build();

            if(facility.getCategoryMain().equals("육아시설")){
                childcare_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("행정시설")) {
                cultural_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("의료시설")) {
                medical_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("산후조리원")) {
                postpartum_facilitieList.add(data);
            }
        }

        FacilityDTO.detailResponse childcare_facility = FacilityDTO.detailResponse.builder()
                .category("granulation")
                .data(childcare_facilitieList)
                .build();

        FacilityDTO.detailResponse cultural_facility = FacilityDTO.detailResponse.builder()
                .category("administration")
                .data(cultural_facilitieList)
                .build();

        FacilityDTO.detailResponse medical_facility = FacilityDTO.detailResponse.builder()
                .category("medical")
                .data(medical_facilitieList)
                .build();

        FacilityDTO.detailResponse postpartum_facility = FacilityDTO.detailResponse.builder()
                .category("confinement")
                .data(postpartum_facilitieList)
                .build();

        List<FacilityDTO.detailResponse> response = new ArrayList<>();

        log.info("childcare_facility size : " + childcare_facility.getData().size());
        log.info("cultural_facility size : " + cultural_facility.getData().size());
        log.info("medical_facility size : " + medical_facility.getData().size());
        log.info("postpartum_facility size : " + postpartum_facility.getData().size());

        response.add(childcare_facility);
        response.add(cultural_facility);
        response.add(medical_facility);
        response.add(postpartum_facility);

        FacilityDTO.response response1 = FacilityDTO.response.builder()
                .facilitiesData(response)
                //.data(response)
                .build();

        return response1;
    }

    public List<FacilityDTO.titleResponse> getFacilitiesTitleWithinRadius(double longitude, double latitude, double radius) {

        List<Facility> facilitiy = facilityRepository.findFacilitiesWithinRadius(longitude, latitude, radius);

        List<FacilityDTO.facilityTitle> childcare_facilitieList = new ArrayList<>();       // 육아시설
        List<FacilityDTO.facilityTitle> cultural_facilitieList = new ArrayList<>();        // 행정시설
        List<FacilityDTO.facilityTitle> medical_facilitieList = new ArrayList<>();         // 의료시설
        List<FacilityDTO.facilityTitle> postpartum_facilitieList = new ArrayList<>();      // 산후조리원

        for (Facility facility : facilitiy) {
            FacilityDTO.facilityTitle data = FacilityDTO.facilityTitle.builder()
                    .facilityId(facility.getId())
                    .facilityName(facility.getFacilityName())
                    .city(facility.getDistrict().getSido_id().getName())
                    .district(facility.getDistrict().getName())
                    .operatingHours(facility.getOperatingHours())
                    .build();

            if(facility.getCategoryMain().equals("육아시설")){
                childcare_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("행정시설")) {
                cultural_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("의료시설")) {
                medical_facilitieList.add(data);
            } else if (facility.getCategoryMain().equals("산후조리원")) {
                postpartum_facilitieList.add(data);
            }
        }

        FacilityDTO.titleResponse childcare_facility = FacilityDTO.titleResponse.builder()
                .category("granulation")
                .data(childcare_facilitieList)
                .build();

        FacilityDTO.titleResponse cultural_facility = FacilityDTO.titleResponse.builder()
                .category("administration")
                .data(cultural_facilitieList)
                .build();

        FacilityDTO.titleResponse medical_facility = FacilityDTO.titleResponse.builder()
                .category("medical")
                .data(medical_facilitieList)
                .build();

        FacilityDTO.titleResponse postpartum_facility = FacilityDTO.titleResponse.builder()
                .category("confinement")
                .data(postpartum_facilitieList)
                .build();

        List<FacilityDTO.titleResponse> response = new ArrayList<>();

        log.info("childcare_facility size : " + childcare_facility.getData().size());
        log.info("cultural_facility size : " + cultural_facility.getData().size());
        log.info("medical_facility size : " + medical_facility.getData().size());
        log.info("postpartum_facility size : " + postpartum_facility.getData().size());


        response.add(childcare_facility);
        response.add(cultural_facility);
        response.add(medical_facility);
        response.add(postpartum_facility);

        return response;
    }

    public FacilityDTO.facilityDetail getDetail(Long id) {
        Facility result = findById(id);

        FacilityDTO.facilityDetail facility = FacilityDTO.facilityDetail.builder()
                .facilityId(result.getId())
                .facilityName(result.getFacilityName())
                .address(result.getAddress())
                .tel(result.getTel())
                .categorySub(result.getCategorySub())
                .city(result.getDistrict().getSido_id().getName())
                .district(result.getDistrict().getName())
                .operatingHours(result.getOperatingHours())
                .longitude(result.getLongitude())
                .latitude(result.getLatitude())
                .build();

        return facility;
    }

    public Facility findById(Long id) {
        Facility result = facilityRepository.findById(id)
                .orElseThrow(() -> new ControllerException(ErrorCode.FACILITY_NOT_EXISTS));

        return result;
    }
}
