package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.Regions;
import wemmy.domain.welfare.Welfare;

import java.util.List;
import java.util.Optional;

@Repository
public interface WelfareRepository extends JpaRepository<Welfare, Long> {

    @Override
    Optional<Welfare> findById(Long id);

    @Query("select w from welfare w where w.hostId = :hostId")
    List<Welfare> findAllByHostId(@Param("hostId") Regions hostId);

    @Override
    List<Welfare> findAll();

    @Query("select w from welfare w where w.hostId = :hostId order by w.view DESC")
    List<Welfare> findAllByOrderByViewAsc(@Param("hostId") Regions hostId);
}
