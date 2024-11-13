package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.repository.welfare.WelfareRegistrationRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<WelfareRegistration> validateBenefit(Welfare welfare, UserEntityV2 user) {

        Optional<WelfareRegistration> result = welfareRegistrationRepository.findByWelfareAndUser(welfare, user);
        return result;

    }
}
