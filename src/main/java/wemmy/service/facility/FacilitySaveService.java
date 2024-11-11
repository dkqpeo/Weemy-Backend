package wemmy.service.facility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.facility.Facility;
import wemmy.domain.user.UserEntity;
import wemmy.dto.facility.FacilitySaveDTO;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilitySaveService {

    private final FacilityService facilityService;
    private final AreaService areaService;
    private final UserService userService;

    public List<FacilitySaveDTO> facilitySave(String fileName) {

        ClassPathResource resource = new ClassPathResource(fileName);  // 크롤링 결과 파일 불러오기.
        List<FacilitySaveDTO> facilityList = new ArrayList<>();

        try {
            // gson 라이브러리를 사용하기 위해 resource를 byte형태로 file Stream을 읽어서, String형식으로 변환.
            byte[] byteData =  FileCopyUtils.copyToByteArray(resource.getInputStream());
            String data = new String(byteData, StandardCharsets.UTF_8);

            //JsonParser parser = new JsonParser();
            JsonElement element = JsonParser.parseString(data);
            JsonArray asJsonArray = element.getAsJsonArray();

            for (JsonElement jsonElement : asJsonArray) {

                JsonObject object = jsonElement.getAsJsonObject();
                FacilitySaveDTO facilitySaveDTO = FacilitySaveDTO.builder()
                        .facilityName(object.get("facilityName").getAsString())
                        .address(object.get("address").getAsString())
                        .tel(object.get("tel").getAsString())
                        .longitude(object.get("longitud").getAsDouble())
                        .latitude(object.get("latitude").getAsDouble())
                        .adminId(object.get("admin_id").getAsString())
                        .categoryMain(object.get("category_main").getAsString())
                        .categorySub(object.get("category_sub").getAsString())
                        .operatingHours(object.get("operating_hours").getAsString())
                        .district(object.get("borough").getAsString())
                        .build();

                facilityList.add(facilitySaveDTO);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return facilityList;
    }

    public void facilityParseAndSave(List<FacilitySaveDTO> facilityList) {

        for (FacilitySaveDTO facility : facilityList) {
            String facilityName = facility.getFacilityName();
            String address = facility.getAddress();
            String tel = facility.getTel();
            double longitude = facility.getLongitude();
            double latitude = facility.getLatitude();
            String categoryMain = facility.getCategoryMain();
            String categorySub = facility.getCategorySub();
            String operatingHours = facility.getOperatingHours();
            String admin = facility.getAdminId();
            String district = facility.getDistrict();

            UserEntity adminId = userService.validateAdmin(admin);        // 관리자 계정
            
            SidoAreas city = areaService.findBySidoName("서울특별시");
            SiggAreas sigg = areaService.findBySiggNameAndSidoId(district, city);

            Facility facility1 = Facility.builder()
                    .facilityName(facilityName)
                    .address(address)
                    .tel(tel)
                    .longitude(longitude)
                    .latitude(latitude)
                    .categoryMain(categoryMain)
                    .categorySub(categorySub)
                    .operatingHours(operatingHours)
                    .adminId(adminId)
                    .district(sigg)
                    .build();

            facilityService.facilitySave(facility1);
        }
    }

}
