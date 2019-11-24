package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.Cuisine;
import pl.com.app.model.enums.Diet;
import pl.com.app.model.enums.Ingredient;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMealParameterDTO {
    private Long id;
    private Set<Cuisine> cuisines;
    private Set<Cuisine> excludeCuisines;
    private Diet diet;
    private Set<Ingredient> includeIngredients;
    private Set<Ingredient> excludeIngredients;
    private UserDTO userDTO;
}
