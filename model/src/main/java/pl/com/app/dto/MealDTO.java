package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.MealPlan;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealDTO {
    private Long id;
    private Long mealId;
    private String title;
    private String image;
    private List<String> imageUrls;
    private Integer readyInMinutes;
    private Integer servings;
}
