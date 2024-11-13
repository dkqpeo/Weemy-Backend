package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;

import java.util.Optional;

@Repository
public interface WelfareRegistrationRepository extends JpaRepository<WelfareRegistration, Long> {

    @Query("select w from welfare_registration w where w.welfare = :welfare AND w.user = :user")
    Optional<WelfareRegistration> findByWelfareAndUser(@Param("welfare") Welfare welfare,
                                                       @Param("user") UserEntityV2 user);

}
