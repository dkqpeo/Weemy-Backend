package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;

import java.util.Optional;

@Repository
public interface ProgramRegistrationRepository extends JpaRepository<ProgramRegistration, Long> {

    @Query("select p from program_registration p where p.program = :program AND p.user = :user")
    Optional<ProgramRegistration> findByProgramAndUser(@Param("program")Program program,
                                                       @Param("user")UserEntityV2 user);
}
