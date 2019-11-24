package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DietRateParameterDTO {
    private Long id;
    private Double fastSlimValue;
    private Double averageSlimValue;
    private Double fastPutOnValue;
    private Double averagePutOnValue;
}
