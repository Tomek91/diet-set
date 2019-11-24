package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.Nutrient;
import pl.com.app.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeByParameterDTO {
    private Long id;
    private Long recipeId;
    private String title;
    private String image;
    private LocalDateTime recipeDateTime;
    private NutrientDTO nutrientDTO;
    private UserDTO userDTO;
}
