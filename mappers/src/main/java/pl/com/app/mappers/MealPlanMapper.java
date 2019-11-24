package pl.com.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.com.app.dto.MealPlanDTO;
import pl.com.app.model.MealPlan;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO"),
            @Mapping(source = "meals", target = "mealDTOs")
    })
    MealPlanDTO mealPlanToMealPlanDTO(MealPlan mealPlan);

    @Mappings({
            @Mapping(source = "userDTO", target = "user"),
            @Mapping(source = "mealDTOs", target = "meals")
    })
    MealPlan mealPlanDTOToMealPlan(MealPlanDTO mealPlanDTO);
}
