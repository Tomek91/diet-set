package pl.com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.app.model.UserMealParameter;

import java.util.Optional;

public interface UserMealParameterRepository extends JpaRepository<UserMealParameter, Long> {

    Optional<UserMealParameter> findByUser_Id(Long userId);
}
