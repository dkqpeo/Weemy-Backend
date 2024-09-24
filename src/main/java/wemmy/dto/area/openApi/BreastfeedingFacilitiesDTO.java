package wemmy.dto.area.openApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreastfeedingFacilitiesDTO {

    private String message;         // 응답 메시지
    private String roomList;        // 응답받은 수유실 목록
    private String room;            // 목록에 존재하는 개별 수유실
    private String roomNo;          // 수유실 번호
    private String roomName;        // 수유실 명
    private String cityName;        // 시/군/구
    private String zoneName;        // 광역시
    private String townName;        // 도로명 번지
    private String roomTypeName;    // 수유실 종류 명
    private String namagerTelNo;    // 관리자 연락처
    private String address;         // 주소
    private String location;        // 상세위치
    private String fatherUseCode;   // 이빠 이용 여부 코드
    private String fatherUseNm;     // 아빠 이용 여부
    private String gpsLat;          // 위도
    private String gpsLong;         // 경도

}
