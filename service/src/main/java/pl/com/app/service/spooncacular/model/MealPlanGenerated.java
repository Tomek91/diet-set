package pl.com.app.service.spooncacular.model;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealPlanGenerated {
    @SerializedName("meals")
    private List<Meal> meals = null;
    @SerializedName("nutrients")
    private Nutrients nutrients;
}
