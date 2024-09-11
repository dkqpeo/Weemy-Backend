package wemmy.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.scrap.ScrapEntity;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<ScrapEntity, Long> {

    @Query("select s from SCRAP s where s.user_id = :userId and s.welfare_id = :welfareId")
    Optional<ScrapEntity> findByUser_idAndWelfare_id(@Param("userId") UserEntity userId,
                                       @Param("welfareId") Welfare welfareId);

    @Query("select s from SCRAP s where s.user_id = :userId ")
    List<ScrapEntity> findByUser_id(@Param("userId") UserEntity userId);
}
