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
import wemmy.repository.welfare.ProgramRepository;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserService;

import java.util.List;

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
}
