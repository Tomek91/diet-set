package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.CaloricDemand;
import pl.com.app.model.UserMealParameter;

import java.util.Optional;

public interface CaloricDemandRepository extends JpaRepository<CaloricDemand, Long> {

    Optional<CaloricDemand> findByUser_Id(Long userId);
}
