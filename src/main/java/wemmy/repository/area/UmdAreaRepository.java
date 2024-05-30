package wemmy.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.area.district.UmdAreas;

import java.util.Optional;

public interface UmdAreaRepository extends JpaRepository<UmdAreas, Long> {

    @Query("select s from umd_areas s where s.adm_code = :admCode")
    Optional<UmdAreas> findByAdm_code(@Param("admCode") String admCode);

}
