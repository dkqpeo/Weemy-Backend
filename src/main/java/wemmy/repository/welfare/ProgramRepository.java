package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.Regions;
import wemmy.domain.welfare.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("select p from program p where p.cityName = :cityName")
    List<Program> findAllByCityName(@Param("cityName")Regions cityName);

}
