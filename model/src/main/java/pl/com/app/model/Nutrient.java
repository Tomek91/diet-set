package pl.com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class Nutrient {

    private Double calories;

    private Double carbohydrates;

    private Double fat;

    private Double protein;
}
