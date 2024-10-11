package wemmy.dto.scrap;

import lombok.Builder;
import lombok.Getter;

public class ScrapDTO {

    @Getter @Builder
    public static class response {

        private Long id;
        private Long userId;
        private Long welfareId;
        private String group;
    }
}
