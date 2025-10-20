package wemmy.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.area.district.UmdAreas;

import java.util.Optional;

@Repository
public interface UmdAreaRepository extends JpaRepository<UmdAreas, Long> {

    @Query("select s from umd_areas s where s.adm_code = :admCode")
    Optional<UmdAreas> findByAdm_code(@Param("admCode") String admCode);

    @Query("select s from umd_areas s where s.adm_code = :admCode and s.sigu_id = :siguId")
    Optional<UmdAreas> findByAdm_codeAndSigu_id(@Param("admCode") String admCode,
                                                @Param("siguId") SiguAreas siguId);

    Optional<UmdAreas> findByName(String name);
}
