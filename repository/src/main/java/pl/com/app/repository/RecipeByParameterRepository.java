package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.RecipeByParameter;

import java.util.Set;

public interface RecipeByParameterRepository extends JpaRepository<RecipeByParameter, Long> {
    Set<RecipeByParameter> findAllByUser_Id(Long userId);
}
