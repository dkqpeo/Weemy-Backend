package wemmy.service.welfare;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Program;
import wemmy.dto.program.ProgramSaveDTO;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProgramSaveService {

    private final ProgramService programService;
    private final UserService userService;
    private final AreaService areaService;

    public List<ProgramSaveDTO> programSave(String fileName) {

        ClassPathResource resource = new ClassPathResource(fileName);  // 크롤링 결과 파일 불러오기.
        List<ProgramSaveDTO> programList = new ArrayList<>();

        try {
            // gson 라이브러리를 사용하기 위해 resource를 byte형태로 file Stream을 읽어서, String형식으로 변환.
            byte[] byteData =  FileCopyUtils.copyToByteArray(resource.getInputStream());
            String data = new String(byteData, StandardCharsets.UTF_8);

            //JsonParser parser = new JsonParser();
            JsonElement element = JsonParser.parseString(data);
            JsonArray asJsonArray = element.getAsJsonArray();

            for (JsonElement jsonElement : asJsonArray) {


                JsonObject object = jsonElement.getAsJsonObject();
                ProgramSaveDTO programSaveDTO = new ProgramSaveDTO();

                programSaveDTO.setTitle(object.get("title").getAsString());
                programSaveDTO.setAplicationPeriod(object.get("aplication_period").getAsString());
                programSaveDTO.setTrainingPeriod(object.get("training_period").getAsString());
                programSaveDTO.setCategory(object.get("category").getAsString());
                programSaveDTO.setCityName(object.get("cityName").getAsString());
                programSaveDTO.setAdminId(object.get("admin_id").getAsString());

                programList.add(programSaveDTO);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return programList;
    }

    public void programParseAndSave(List<ProgramSaveDTO> programList) {
        System.out.println("진입");

        for (ProgramSaveDTO program : programList) {
            String title = program.getTitle();
            String aplicationPeriod = program.getAplicationPeriod();
            String trainingPeriod = program.getTrainingPeriod();
            String category = program.getCategory();
            String cityName = program.getCityName();
            String admin = program.getAdminId();

            SidoAreas sido = areaService.findBySidoName("서울특별시");

            SiggAreas sigg = areaService.findBySiggNameAndSidoId(cityName, sido);
            String sidoCode = sigg.getSido_id().getAdm_code();
            String siggCode = sigg.getAdm_code();

            UserEntity adminId = userService.validateAdmin(admin);        // 관리자 계정
            Regions region = areaService.validateRegionCode(sidoCode+siggCode+"00000");    // 지역코드

            String imageUrl = "";
            if(region.getSigg_id().getName().equals("금천구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/geumcheon-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("구로구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/guro-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("서초구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seocho-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("강남구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangnam-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("강서구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/ganseo-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("관악구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gwanak-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("광진구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gwangjin-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("동작구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dongjak-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("영등포구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yeongdeungpo-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("송파구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/songpa-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("강동구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangdong-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("양천구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yangcheon-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("마포구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/mapo-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("서대문구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seodaemun-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("은평구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/eunpyeong-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("노원구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/nowon-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("도봉구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dobong-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("강북구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/gangbuk-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("성북구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/sungbuk-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("종로구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/jongno-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("중구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/jung-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("중랑구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/_jungnang-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("동대문구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/dongdaemun-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("성동구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/seongdong-gu.png?raw=true";
            } else if (region.getSigg_id().getName().equals("용산구")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/yongsan-gu.png?raw=true";
            } else if(region.getSigg_id().getName().equals("정부")) {
                imageUrl = "https://github.com/Team-Wemmy/Wemmy-City-Image/blob/main/government.png?raw=true";
            }

            Program program1 = Program.builder()
                    .title(title)
                    .aplicationPeriod(aplicationPeriod)
                    .trainingPeriod(trainingPeriod)
                    .category(category)
                    .cityName(region)
                    .adminId(adminId)
                    .imageUrl(imageUrl)
                    .build();

            programService.programSave(program1);

            System.out.println("저장 완료");
        }

    }

}
