package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.Meal;
import pl.com.app.model.Nutrient;
import pl.com.app.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealPlanDTO {
    private Long id;
    private Nutrient nutrient;
    private LocalDateTime mealPlanDateTime;
    private Set<MealDTO> mealDTOs;
    private UserDTO userDTO;
}
