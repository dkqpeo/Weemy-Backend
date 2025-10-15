package wemmy.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;

import java.util.Optional;

@Repository
public interface SiguAreaRepository extends JpaRepository<SiguAreas, Long> {

    Optional<SiguAreas> findByName(String name);

    @Query("select s from sigu_areas s where sido_areas = :sidoId and s.adm_code = :admCode")
    Optional<SiguAreas> findBySidoAndAdm_code(@Param("sidoId") SidoAreas sidoId, @Param("admCode") String admCode);

    @Query("select s from sigu_areas s where s.adm_code = :admCode")
    Optional<SiguAreas> findByAdm_code(@Param("admCode") String admCode);

    @Query("select s from sigu_areas s where s.name =:name and s.sido_id = :sidoId")
    Optional<SiguAreas> findByNameAndSido_id(@Param("name") String name, @Param("sidoId") SidoAreas sidoId);
}
