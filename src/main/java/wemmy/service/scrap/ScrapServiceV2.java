package wemmy.service.scrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.scrap.ScrapEntity;
import wemmy.domain.scrap.ScrapEntityV2;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ServiceException;
import wemmy.repository.scrap.ScrapRepository;
import wemmy.repository.scrap.ScrapRepositoryV2;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScrapServiceV2 {

    private final ScrapRepositoryV2 scrapRepository;
    private final WelfareService welfareService;
    private final ProgramService programService;

    /**
     * 복지정보 스크랩 저장.
     */
    public void scrapSaveByBenefit(UserEntityV2 user, Welfare welfare) {

        validateScrapByBenefit(user, welfare);

        ScrapEntityV2 scrap = ScrapEntityV2.builder()
                .user_id(user)
                .welfare_id(welfare)
                .group("benefit")
                .build();

        scrapRepository.save(scrap);
    }

    /**
     * 프로그램정보 스크랩 저장.
     */
    public void scrapSaveByProgram(UserEntityV2 user, Program program) {

        validateScrapByProgram(user, program);

        ScrapEntityV2 scrap = ScrapEntityV2.builder()
                .user_id(user)
                .program_id(program)
                .group("program")
                .build();

        scrapRepository.save(scrap);
    }

    /**
     * 해당 회원의 복지정보 스크랩 리스트 조회
     */
    public List<ScrapDTO.response> scrapList(UserEntityV2 user) {

        List<ScrapEntityV2> scrapList = scrapRepository.findByUser_id(user);

        List<ScrapDTO.response> responseList = new ArrayList<>();
        for (ScrapEntityV2 scrap : scrapList) {
            Long welfareId = null;
            String group = "";

            if(scrap.getGroup().equals("benefit")){
                welfareId = scrap.getWelfare_id().getId();
                group = "benefit";
            } else if (scrap.getGroup().equals("program")) {
                welfareId = scrap.getProgram_id().getId();
                group = "program";
            }

            ScrapDTO.response dto = ScrapDTO.response.builder()
                    .id(scrap.getId())
                    .userId(scrap.getUser_id().getId())
                    .welfareId(welfareId)
                    .group(group)
                    .build();

            responseList.add(dto);
        }

        return responseList;
    }

    /**
     * 회원 Id와 복지정보 Id를 기준으로
     * 스크랩 여부 확인
     */
    public String findScrap(UserEntityV2 user, Long reqId, String group) {

        Optional<ScrapEntityV2> scrapEntityV2 = null;

        if(group.equals("benefit")) {
            Welfare welfare = welfareService.findById(reqId);
            scrapEntityV2 = scrapRepository.findByUser_idAndWelfare_id(user, welfare);
        } else if (group.equals("program")) {
            Program program = programService.findById(reqId);
            scrapEntityV2 = scrapRepository.findByUser_idAndProgram_id(user, program);
        }


        if(scrapEntityV2.isPresent())
            return "true";
        else
            return "false";
    }

    /**
     * 스크랩 복지 정보 중복 여부 확인.
     */
    private void validateScrapByBenefit(UserEntityV2 user, Welfare welfare) {
        Optional<ScrapEntityV2> scrapEntityV2 = scrapRepository.findByUser_idAndWelfare_id(user, welfare);

        if(scrapEntityV2.isPresent())
             throw new ServiceException(ErrorCode.ALREADY_REGISTERED_SCRAP);
    }

    /**
     * 스크랩 복지 정보 중복 여부 확인.
     */
    private void validateScrapByProgram(UserEntityV2 user, Program program) {
        Optional<ScrapEntityV2> scrapEntityV2 = scrapRepository.findByUser_idAndProgram_id(user, program);

        if(scrapEntityV2.isPresent())
            throw new ServiceException(ErrorCode.ALREADY_REGISTERED_SCRAP);
    }
}
