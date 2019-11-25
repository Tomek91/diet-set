package pl.com.app.service.spooncacular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.app.model.CaloricDemand;
import pl.com.app.model.UserMealParameter;
import pl.com.app.model.enums.Cuisine;
import pl.com.app.model.enums.Ingredient;
import pl.com.app.service.spooncacular.model.MealByParameters;
import pl.com.app.service.spooncacular.model.MealPlanGenerated;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpooncacularService {

    private final RestTemplate restTemplate;

    @Value("${apiKey}")
    private String apiKey;


    private String generateParams(Map<String, String> paramsMap) {
        return paramsMap
                .entrySet()
                .stream()
                .map(x -> String.join("=", x.getKey(), x.getValue()))
                .reduce("apiKey=" + apiKey, (a, b) -> String.join("&", a, b));
    }

    //https://api.spoonacular.com/recipes/complexSearch?query=pasta&number=4&apiKey=*****************&minCalories=400&maxCalories=550&minCarbs=0&minProtein=0&minFat=0
    public MealByParameters findRecipeByParameters(CaloricDemand caloricDemand, UserMealParameter userMealParameter) {

        Integer mealNumber = 5;
        Integer caloriesByOneMeal = caloricDemand.getDietValue() / mealNumber;
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("query", "pasta");
        paramsMap.put("number", mealNumber.toString());
        paramsMap.put("minCalories", String.valueOf(caloriesByOneMeal - 50));
        paramsMap.put("maxCalories", String.valueOf(caloriesByOneMeal + 50));
        paramsMap.put("minCarbs", "0");
        paramsMap.put("minProtein", "0");
        paramsMap.put("minFat", "0");

        if (userMealParameter.getCuisines() != null && !userMealParameter.getCuisines().isEmpty()){
            paramsMap.put("cuisine", commaSeparatedCuisine(userMealParameter.getCuisines()));
        }
        if (userMealParameter.getDiet() != null){
            paramsMap.put("diet", userMealParameter.getDiet().toString());
        }
        if (userMealParameter.getExcludeCuisines() != null && !userMealParameter.getExcludeCuisines().isEmpty()){
            paramsMap.put("excludeCuisine", commaSeparatedCuisine(userMealParameter.getExcludeCuisines()));
        }
        if (userMealParameter.getIncludeIngredients() != null && !userMealParameter.getIncludeIngredients().isEmpty()){
            paramsMap.put("includeIngredients", commaSeparatedIngredients(userMealParameter.getExcludeIngredients()));
        }
        if (userMealParameter.getExcludeIngredients() != null && !userMealParameter.getExcludeIngredients().isEmpty()){
            paramsMap.put("excludeIngredients", commaSeparatedIngredients(userMealParameter.getExcludeIngredients()));
        }

        String generatedParams = generateParams(paramsMap);
        ResponseEntity<MealByParameters> responseEntity
                = restTemplate.exchange(CommunicationConfig.BASE_URL + "complexSearch?" + generatedParams,
                HttpMethod.GET,
                null,
                MealByParameters.class
        );

        return responseEntity.getBody();
    }

    private String commaSeparatedIngredients(Set<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(Ingredient::toString)
                .collect(Collectors.joining(","));
    }

    private String commaSeparatedCuisine(Set<Cuisine> cuisines) {
        return cuisines
                .stream()
                .map(Cuisine::toString)
                .collect(Collectors.joining(","));
    }

    //https://api.spoonacular.com/recipes/mealplans/generate?apiKey=***************&targetCalories=1119&timeFrame=day
    public MealPlanGenerated findMealPlan(CaloricDemand caloricDemand) {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("targetCalories", caloricDemand.getDietValue().toString());
        paramsMap.put("timeFrame", "day");

        String generatedParams = generateParams(paramsMap);
        ResponseEntity<MealPlanGenerated> responseEntity
                = restTemplate.exchange(CommunicationConfig.BASE_URL + "mealplans/generate?" + generatedParams,
                HttpMethod.GET,
                null,
                MealPlanGenerated.class
        );

        return responseEntity.getBody();
    }
}
