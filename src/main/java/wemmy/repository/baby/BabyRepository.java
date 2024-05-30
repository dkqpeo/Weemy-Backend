package wemmy.repository.baby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BabyRepository extends JpaRepository<BabyEntity, Long> {

    Optional<BabyEntity> findByUserId(Long id);

    List<BabyEntity> findAllByUserId(Long id);
}
