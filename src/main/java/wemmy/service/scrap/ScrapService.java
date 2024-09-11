package wemmy.service.scrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.scrap.ScrapEntity;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ServiceException;
import wemmy.repository.scrap.ScrapRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {

    private final ScrapRepository scrapRepository;

    /**
     * 복지정보 스크랩 저장.
     */
    public void scrapSave(UserEntity user, Welfare welfare) {

        validateScrap(user, welfare);

        ScrapEntity scrap = ScrapEntity.builder()
                .user_id(user)
                .welfare_id(welfare)
                .build();


        scrapRepository.save(scrap);
    }

    public List<ScrapDTO.response> scrapList(UserEntity user) {

        List<ScrapEntity> scrapList = scrapRepository.findByUser_id(user);

        List<ScrapDTO.response> responseList = new ArrayList<>();
        for (ScrapEntity scrap : scrapList) {
            ScrapDTO.response dto = ScrapDTO.response.builder()
                    .id(scrap.getId())
                    .userId(scrap.getUser_id().getId())
                    .welfareId(scrap.getWelfare_id().getId())
                    .build();

            responseList.add(dto);
        }

        return responseList;
    }

    /**
     * 스크랩 복지 정보 중복 여부 확인.
     */
    private void validateScrap(UserEntity user, Welfare welfare) {
        Optional<ScrapEntity> scrapEntity = scrapRepository.findByUser_idAndWelfare_id(user, welfare);

        if(scrapEntity.isPresent())
             throw new ServiceException(ErrorCode.ALREADY_REGISTERED_SCRAP);
    }

}
