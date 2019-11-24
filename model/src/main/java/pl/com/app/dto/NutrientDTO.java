package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NutrientDTO {
    private Double calories;
    private Double carbohydrates;
    private Double fat;
    private Double protein;
}
