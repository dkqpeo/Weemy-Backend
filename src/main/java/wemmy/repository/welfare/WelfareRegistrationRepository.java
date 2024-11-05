package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.WelfareRegistration;

@Repository
public interface WelfareRegistrationRepository extends JpaRepository<WelfareRegistration, Long> {

}
