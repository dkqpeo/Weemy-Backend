package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.welfare.ProgramRegistration;

@Repository
public interface ProgramRegistrationRepository extends JpaRepository<ProgramRegistration, Long> {

}
