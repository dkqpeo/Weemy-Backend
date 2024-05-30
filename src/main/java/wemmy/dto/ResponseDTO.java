package wemmy.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDTO {

    private String message;

    public static ResponseDTO of (String message) {
        return ResponseDTO.builder()
                .message(message)
                .build();
    }
}
