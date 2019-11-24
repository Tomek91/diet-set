package pl.com.app.service.spooncacular.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Nutrients {
    @SerializedName("calories")
    private Double calories;
    @SerializedName("carbohydrates")
    private Double carbohydrates;
    @SerializedName("fat")
    private Double fat;
    @SerializedName("protein")
    private Double protein;
}
