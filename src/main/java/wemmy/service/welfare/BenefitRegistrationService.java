package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.repository.welfare.WelfareRegistrationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BenefitRegistrationService {

    private final WelfareRegistrationRepository welfareRegistrationRepository;

    public void register(WelfareRegistration register) {
        welfareRegistrationRepository.save(register);
    }

    public List<WelfareRegistration> findAll() {
        List<WelfareRegistration> welfareList = welfareRegistrationRepository.findAll();

        return welfareList;
    }
}
