package wemmy.repository.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.facility.Facility;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query(
            value = "SELECT * FROM facility f " +
                    "WHERE ST_Distance_Sphere(point(f.longitude, f.latitude), point(:longitude, :latitude)) <= :radius",
            nativeQuery = true)
    List<Facility> findFacilitiesWithinRadius(@Param("longitude") double longitude,
                                              @Param("latitude") double latitude,
                                              @Param("radius") double radius);
}
