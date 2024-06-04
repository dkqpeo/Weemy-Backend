package wemmy.api.benefit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.service.benefit.BenefitService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wemmy/benefit")
public class BenefitDetailController {

    private final BenefitService benefitService;

    /**
     * 복지 내용 상세조회
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<BenefitDTO.response> getBenefitDetail(@PathVariable("id") Long id) {

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getBenefitDetail(id);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }
}
