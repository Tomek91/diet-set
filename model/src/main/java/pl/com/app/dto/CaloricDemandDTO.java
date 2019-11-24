package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.DietType;

import javax.persistence.Column;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CaloricDemandDTO {
    private Long id;
    private Integer nominalValue;
    private Integer dietValue;
    private Double weight;
    private Integer height;
    private DietType dietType;
    private UserDTO userDTO;
}
