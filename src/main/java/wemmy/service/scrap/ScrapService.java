package wemmy.service.scrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.scrap.ScrapEntity;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ServiceException;
import wemmy.repository.scrap.ScrapRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public void scrapSave(UserEntity user, Welfare welfare) {

        validateScrap(user, welfare);

        ScrapEntity scrap = ScrapEntity.builder()
                .user_id(user)
                .welfare_id(welfare)
                .build();


        scrapRepository.save(scrap);
    }

    private void validateScrap(UserEntity user, Welfare welfare) {
        Optional<ScrapEntity> scrapEntity = scrapRepository.findByUser_idAndWelfare_id(user, welfare);

        if(scrapEntity.isPresent())
             throw new ServiceException(ErrorCode.ALREADY_REGISTERED_SCRAP);
    }

}
