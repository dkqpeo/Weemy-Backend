package wemmy.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.city.SidoAreas;

import java.util.Optional;

@Repository
public interface SidoAreaRepository extends JpaRepository<SidoAreas, Long> {

    Optional<SidoAreas> findByName(String name);

    @Query("select  s from sido_areas  s where s.adm_code = :admCode")
    Optional<SidoAreas> findByAdm_code(@Param("admCode") String admCode);
}
