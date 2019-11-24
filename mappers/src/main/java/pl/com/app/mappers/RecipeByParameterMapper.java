package pl.com.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.com.app.dto.RecipeByParameterDTO;
import pl.com.app.model.RecipeByParameter;

@Mapper(componentModel = "spring")
public interface RecipeByParameterMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO"),
            @Mapping(source = "nutrient", target = "nutrientDTO")
    })
    RecipeByParameterDTO recipeByParameterToRecipeByParameterDTO(RecipeByParameter recipeByParameter);

    @Mappings({
            @Mapping(source = "userDTO", target = "user"),
            @Mapping(source = "nutrientDTO", target = "nutrient")
    })
    RecipeByParameter recipeByParameterDTOToRecipeByParameter(RecipeByParameterDTO recipeByParameterDTO);
}
