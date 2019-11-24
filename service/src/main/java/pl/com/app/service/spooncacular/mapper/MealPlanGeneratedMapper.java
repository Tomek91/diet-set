package pl.com.app.service.spooncacular.mapper;

import org.springframework.stereotype.Service;
import pl.com.app.model.Meal;
import pl.com.app.model.MealPlan;
import pl.com.app.model.Nutrient;
import pl.com.app.model.RecipeByParameter;
import pl.com.app.service.spooncacular.model.MealPlanGenerated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public interface MealPlanGeneratedMapper {

    static MealPlan mealPlanGeneratedToMealPlan(MealPlanGenerated mealPlanGenerated){
        return mealPlanGenerated == null ? null : MealPlan.builder()
                .mealPlanDateTime(LocalDateTime.now())
                .meals(toCollectionMeal(mealPlanGenerated.getMeals()))
                .nutrient(
                        Nutrient.builder()
                                .calories(mealPlanGenerated.getNutrients().getCalories())
                                .carbohydrates(mealPlanGenerated.getNutrients().getCarbohydrates())
                                .fat(mealPlanGenerated.getNutrients().getFat())
                                .protein(mealPlanGenerated.getNutrients().getProtein())
                                .build()
                )
                .build();
    }

    private static Set<Meal> toCollectionMeal(List<pl.com.app.service.spooncacular.model.Meal> meals) {
        return meals
                .stream()
                .map(MealPlanGeneratedMapper::toMeal)
                .collect(Collectors.toSet());
    }

    private static Meal toMeal(pl.com.app.service.spooncacular.model.Meal meal) {
        return meal == null ? null : Meal.builder()
                .image(meal.getImage())
                .imageUrls(meal.getImageUrls())
                .mealId(Long.valueOf(meal.getId()))
                .servings(meal.getServings())
                .title(meal.getTitle())
                .readyInMinutes(meal.getReadyInMinutes())
                .build();
    }
}
