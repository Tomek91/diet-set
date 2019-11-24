package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.MealPlan;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
}
