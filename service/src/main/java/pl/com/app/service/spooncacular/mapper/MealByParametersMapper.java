package pl.com.app.service.spooncacular.mapper;

import org.springframework.stereotype.Service;
import pl.com.app.model.Nutrient;
import pl.com.app.model.RecipeByParameter;
import pl.com.app.model.enums.NutrientType;
import pl.com.app.service.spooncacular.model.MealByParameters;
import pl.com.app.service.spooncacular.model.Nutrition;
import pl.com.app.service.spooncacular.model.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public interface MealByParametersMapper {

    static Set<RecipeByParameter> mealByParametersToRecipeByParameterCollection(MealByParameters mealByParameters){
        return mealByParameters == null || mealByParameters.getResults() == null ? null :
                mealByParameters.getResults()
                        .stream()
                        .map(MealByParametersMapper::toRecipeByParameter)
                        .collect(Collectors.toSet());
    }

    private static RecipeByParameter toRecipeByParameter(Result result) {
        return result == null ? null : RecipeByParameter.builder()
                .image(result.getImage())
                .recipeId(Long.valueOf(result.getId()))
                .recipeDateTime(LocalDateTime.now())
                .title(result.getTitle())
                .nutrient(
                        Nutrient.builder()
                                .calories(getNutrientByName(NutrientType.CALORIES.toString(), result.getNutrition()))
                                .carbohydrates(getNutrientByName(NutrientType.CARBOHYDRATES.toString(), result.getNutrition()))
                                .fat(getNutrientByName(NutrientType.FAT.toString(), result.getNutrition()))
                                .protein(getNutrientByName(NutrientType.PROTEIN.toString(), result.getNutrition()))
                                .build()
                )
                .build();
    }

    private static Double getNutrientByName(String nutrientName, List<Nutrition> nutrition) {
        return nutrition == null ? null : nutrition
                .stream()
                .filter(x -> x.getTitle().equalsIgnoreCase(nutrientName))
                .findFirst()
                .map(Nutrition::getAmount)
                .orElse(null);
    }
}



