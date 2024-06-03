package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.welfare.Wcategory;
import wemmy.domain.welfare.Welfare;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.welfare.WcategoryRepository;
import wemmy.repository.welfare.WelfareRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WelfareService {

    private final WelfareRepository welfareRepository;
    private final WcategoryRepository wcategoryRepository;

    public void welfareSave(Welfare welfare) {
        welfareRepository.save(welfare);
    }

    public Wcategory getWcategoryByWcategoryId(Long id) {
        Wcategory result = wcategoryRepository.findById(id)
                .orElseThrow(() -> new ControllerException(ErrorCode.NOT_FOUND_WCATEGORY_ID));
        return result;
    }
}
