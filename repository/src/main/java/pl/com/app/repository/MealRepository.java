package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
