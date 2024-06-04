package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.area.Regions;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.benefit.BenefitDTO;

import java.util.List;

@Repository
public interface WelfareRepository extends JpaRepository<Welfare, Long> {

    @Query("select w from welfare w where w.hostId = :hostId")
    List<Welfare> findAllByHostId(@Param("hostId") Regions hostId);
}
