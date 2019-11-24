package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.DietRateParameter;

public interface DietRateParameterRepository extends JpaRepository<DietRateParameter, Long> {
}
