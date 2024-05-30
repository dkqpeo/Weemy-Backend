package wemmy.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.Regions;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Regions, Long> {

    Optional<Regions> findById(Long regionId);

    @Query("select r from regions r where r.region_cd = :regionCd")
    Optional<Regions> findByRegionCd(@Param("regionCd") String regionCd);
}
