package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.welfare.Welfare;

@Repository
public interface WelfareRepository extends JpaRepository<Welfare, Long> {
}
