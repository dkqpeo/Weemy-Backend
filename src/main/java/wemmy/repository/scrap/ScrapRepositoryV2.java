package wemmy.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.scrap.ScrapEntityV2;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Welfare;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepositoryV2 extends JpaRepository<ScrapEntityV2, Long> {

    @Query("select s from SCRAPV2 s where s.user_id = :userId and s.welfare_id = :welfareId")
    Optional<ScrapEntityV2> findByUser_idAndWelfare_id(@Param("userId") UserEntityV2 userId,
                                       @Param("welfareId") Welfare welfareId);

    @Query("select s from SCRAPV2 s where s.user_id = :userId and s.program_id = :programId")
    Optional<ScrapEntityV2> findByUser_idAndProgram_id(@Param("userId") UserEntityV2 userId,
                                                       @Param("programId") Program programId);

    @Query("select s from SCRAPV2 s where s.user_id = :userId ")
    List<ScrapEntityV2> findByUser_id(@Param("userId") UserEntityV2 userId);

}
