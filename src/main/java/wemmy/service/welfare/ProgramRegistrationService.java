package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.repository.welfare.ProgramRegistrationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProgramRegistrationService {

    private final ProgramRegistrationRepository programRegistrationRepository;

    public void register(ProgramRegistration register) {
        programRegistrationRepository.save(register);
    }

    public List<ProgramRegistration> findAll() {
        List<ProgramRegistration> programList = programRegistrationRepository.findAll();

        return programList;
    }
}
