package wemmy.service.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Program;
import wemmy.dto.program.ProgramSaveDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.welfare.ProgramRepository;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;

    public void programSave(Program program) {

        programRepository.save(program);
        System.out.println("저장 완료");
    }

    public List<Program> findAllProgramByRegions(Regions regions) {

        List<Program> programList = programRepository.findAllByCityName(regions);

        return programList;
    }

    public Program findById(Long reqBenefitId) {
        Program program = programRepository.findById(reqBenefitId)
                .orElseThrow(() -> new ControllerException(ErrorCode.NOT_FOUND_WCATEGORY_ID));

        return program;
    }
}
