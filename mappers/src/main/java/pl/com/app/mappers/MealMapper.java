package pl.com.app.mappers;

import org.mapstruct.Mapper;
import pl.com.app.dto.MealDTO;
import pl.com.app.model.Meal;

@Mapper(componentModel = "spring")
public interface MealMapper {
    MealDTO mealToMealDTO(Meal meal);
    Meal mealDTOToMeal(MealDTO mealDTO);
}
