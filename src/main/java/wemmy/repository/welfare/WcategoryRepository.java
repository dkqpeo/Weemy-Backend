package wemmy.repository.welfare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemmy.domain.welfare.Wcategory;

import java.util.Optional;

@Repository
public interface WcategoryRepository extends JpaRepository<Wcategory, Long> {

    @Override
    Optional<Wcategory> findById(Long aLong);

    Optional<Wcategory> findByName(String name);
}
