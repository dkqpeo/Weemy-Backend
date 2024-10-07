package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.welfare.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
