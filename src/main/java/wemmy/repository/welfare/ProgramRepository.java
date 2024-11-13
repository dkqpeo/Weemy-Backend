package wemmy.repository.welfare;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.Regions;
import wemmy.domain.welfare.Program;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("select p from program p where p.cityName = :cityName")
    List<Program> findAllByCityName(@Param("cityName")Regions cityName);

    @Query("select p from program p where p.cityName = :cityName order by p.view DESC")
    List<Program> findAllByOrderByViewAsc(@Param("cityName")Regions cityName);

}
