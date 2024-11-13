package wemmy.global.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // test
    TEST(HttpStatus.BAD_REQUEST, "001", "test error"),

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 access token이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),
    NOT_EQUAL_PASSWORD(HttpStatus.UNAUTHORIZED, "A-009", "Password가 일치하지 않습니다."),
    NOT_EQUAL_CODE(HttpStatus.UNAUTHORIZED, "A-010", "Email Code가 일치하지 않습니다."),
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "A-011", "해당 회원에게 접근 권한이 없습니다."),

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입입니다. (memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원입니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),
    NOT_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "M-004", "일치하는 Email 정보가 존재하지 않습니다."),
    NOT_EXISTS_MEMBER(HttpStatus.BAD_REQUEST, "M-005", "회원정보가 일치하지 않습니다."),
    USERNAME_IS_NULL(HttpStatus.BAD_REQUEST, "M-006", "username이 null 입니다."),
    NOT_VALID_CALENDAR(HttpStatus.BAD_REQUEST, "M-007", "해당 회원에게 접근 권한이 없는 일정입니다."),
    NOT_OFFICE_USER(HttpStatus.BAD_REQUEST, "M-008", "Office User가 아닙니다."),
    NOT_ADMIN_USER(HttpStatus.BAD_REQUEST, "M-009", "Admin User가 아닙니다."),

    // 아기
    BABY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "B-001", "아기 정보가 존재하지 않습니다."),

    // 지역
    CITY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AREA-001", "해당 지역시가 존재하지 않습니다."),
    DISTRICT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AREA-002", "해당 자치구가 존재하지 않습니다."),
    UMD_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AREA-003", "해당 읍면동이 존재하지 않습니다."),
    REGION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AREA-004", "해당 region code가 존재하지 않습니다."),

    // 복지
    NOT_FOUND_WCATEGORY_ID(HttpStatus.BAD_REQUEST, "W-001", "해당 복지 카테고리 code가 존재하지 않습니다."),

    // 스크랩
    ALREADY_REGISTERED_SCRAP(HttpStatus.BAD_REQUEST, "S-001", "이미 스크랩 되어있는 복지 정보 입니다."),

    // 주변시설
    FACILITY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "F-001", "존재하지 않는 Facility Key 입니다."),

    // 프로그램 신청
    ALREADY_REGISTERED_PROGRAM(HttpStatus.BAD_REQUEST, "P-001", "이미 신청한 프로그램 입니다."),;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
