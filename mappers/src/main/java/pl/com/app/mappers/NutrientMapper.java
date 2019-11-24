package pl.com.app.mappers;

import org.mapstruct.Mapper;
import pl.com.app.dto.NutrientDTO;
import pl.com.app.model.Nutrient;

@Mapper(componentModel = "spring")
public interface NutrientMapper {
    NutrientDTO nutrientToNutrientDTO(Nutrient nutrient);
    Nutrient nutrientDTOToNutrient(NutrientDTO nutrientDTO);
}
