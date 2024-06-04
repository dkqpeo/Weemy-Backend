package wemmy.service.benefit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import wemmy.dto.benefit.BenefitSaveDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BenefitSaveService {

    public List<BenefitSaveDTO> benefitSave() {

        ClassPathResource resource = new ClassPathResource("/benefit/geumcheon.json");  // 크롤링 결과 파일 불러오기.
        List<BenefitSaveDTO> benefitList = new ArrayList<>();

        try {
            // gson 라이브러리를 사용하기 위해 resource를 byte형태로 file Stream을 읽어서, String형식으로 변환.
            byte[] byteData =  FileCopyUtils.copyToByteArray(resource.getInputStream());
            String data = new String(byteData, StandardCharsets.UTF_8);

            //JsonParser parser = new JsonParser();
            JsonElement element = JsonParser.parseString(data);
            JsonArray asJsonArray = element.getAsJsonArray();

            for (JsonElement jsonElement : asJsonArray) {

                JsonObject object = jsonElement.getAsJsonObject();
                BenefitSaveDTO benefitSaveDTO = new BenefitSaveDTO();

                benefitSaveDTO.setUnique_id(object.get("unique_id").getAsLong());
                benefitSaveDTO.setAdmin_id("teamWemmy@gmail.com");
                benefitSaveDTO.setW_category_id(object.get("w_category_id").getAsInt());
                benefitSaveDTO.setHost_id(object.get("host_id").getAsString());
                benefitSaveDTO.setTitle(object.get("title").getAsString());
                benefitSaveDTO.setField(object.get("field").getAsString());
                benefitSaveDTO.setContent(object.get("content").getAsString());
                benefitSaveDTO.setWay(object.get("way").getAsString());
                // benefitSaveDTO.setInquiry(object.get("inquiry").getAsString());
                benefitSaveDTO.setEtc(object.get("etc").getAsString());
                benefitSaveDTO.setOriginal_url(object.get("original_url").getAsString());

                benefitList.add(benefitSaveDTO);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return benefitList;
    }
}
