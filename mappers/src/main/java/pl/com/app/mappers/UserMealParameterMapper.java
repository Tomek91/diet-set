package pl.com.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.com.app.dto.UserMealParameterDTO;
import pl.com.app.model.UserMealParameter;

@Mapper(componentModel = "spring")
public interface UserMealParameterMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO")
    })
    UserMealParameterDTO userMealParameterToUserMealParameterDTO(UserMealParameter userMealParameter);

    @Mappings({
            @Mapping(source = "userDTO", target = "user")
    })
    UserMealParameter userMealParameterDTOToUserMealParameter(UserMealParameterDTO userMealParameterDTO);
}
