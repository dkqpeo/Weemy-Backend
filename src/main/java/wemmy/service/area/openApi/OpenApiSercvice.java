package wemmy.service.area.openApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wemmy.dto.area.openApi.OpenApiRespDTO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpenApiSercvice {
    private String serviceKey;

    public OpenApiSercvice(@Value("${openApi.key}") String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public List<OpenApiRespDTO> getOpenApi() {
        List<OpenApiRespDTO> regionList = new ArrayList<>();

        try {
            // OpenApi에 데이터 요청.
            URL url = requestOpenApi();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            // 응답결과 확인
            log.info("Response code: {}", conn.getResponseCode());

            // 응답결과 저장.
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // json 파싱
            JsonParser parser = new JsonParser();
            // 문자열을 JsonElement로 변환
            JsonElement element = parser.parseString(sb.toString());
            // element를 JsonObject로 변환
            JsonObject object = element.getAsJsonObject();
            // StanReginCd 추출
            JsonArray stanreginCd = object.getAsJsonArray("StanReginCd");

            // "StanReginCd" 배열을 순회하며 sido_cd 값 출력
            for (JsonElement jsonElement : stanreginCd) {
                JsonObject obj = jsonElement.getAsJsonObject();

                if(obj.has("row")){
                    JsonArray row = obj.getAsJsonArray("row");
                    for (JsonElement element1 : row) {
                        JsonObject rowObj = element1.getAsJsonObject();

                        OpenApiRespDTO dto = new OpenApiRespDTO();

                        // 파싱
                        if(rowObj.has("sido_cd")) {
                            dto.setSido_cd(rowObj.get("sido_cd").getAsString());
                        } if(rowObj.has("sgg_cd")) {
                            dto.setSigu_cd(rowObj.get("sgg_cd").getAsString());
                        } if(rowObj.has("umd_cd")) {
                            dto.setUmd_cd(rowObj.get("umd_cd").getAsString());
                        } if(rowObj.has("region_cd")) {
                            dto.setRegion_cd(rowObj.get("region_cd").getAsString());
                        }
                        dto.setLocallow_nm(rowObj.get("locallow_nm").getAsString());

                        // DTO객체를 리스트에 추가
                        regionList.add(dto);
                    }
                }
            }
            rd.close();
            conn.disconnect();

            return regionList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL requestOpenApi() throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8")+ "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*호출문서(xml, json) default : xml*/
        urlBuilder.append("&" + URLEncoder.encode("locatadd_nm","UTF-8") + "=" + URLEncoder.encode("서울특별시", "UTF-8")); /*지역주소명*/

        return new URL(urlBuilder.toString());
    }
}
