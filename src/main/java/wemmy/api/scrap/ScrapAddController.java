package wemmy.api.scrap;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Scrap", description = "복지 정보 스크랩 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/scrap")
public class ScrapAddController {


}
