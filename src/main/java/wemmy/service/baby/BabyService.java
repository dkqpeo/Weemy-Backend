package wemmy.service.baby;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.dto.baby.BabyRespDTO;
import wemmy.dto.baby.BabyUpdateInfoDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.BabyException;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.repository.baby.BabyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BabyService {

    private final BabyRepository babyRepository;

    public void insert(BabyEntity baby) {

        babyRepository.save(baby);
    }

    public void updateBabyInfo(Long userId, BabyUpdateInfoDTO dto) {

        BabyEntity babyInfo = getByUserId(userId)
                .orElseThrow(() -> new BabyException(ErrorCode.BABY_NOT_EXISTS));

        BabyType type = null;
        if(dto.getType() != null) {
            type = BabyType.from(dto.getType());
        }

        babyInfo.updateBabyInfo(dto.getName(), dto.getBirthday(), type);
    }

    public List<BabyRespDTO> babyList(Long userId) {
        List<BabyEntity> findList = babyRepository.findAllByUserId(userId);
        List<BabyRespDTO> babyList = new ArrayList<>();

        for (BabyEntity baby : findList) {
            babyList.add(new BabyRespDTO(baby));
        }
        return babyList;
    }

    public BabyEntity findBabyByUserId(Long userId) {
        BabyEntity babyEntity = babyRepository.findByUserId(userId)
                .orElseThrow(() -> new ControllerException(ErrorCode.BABY_NOT_EXISTS));
        return babyEntity;
    }

    private Optional<BabyEntity> getByUserId(Long userId) {
        return babyRepository.findByUserId(userId);
    }
}
