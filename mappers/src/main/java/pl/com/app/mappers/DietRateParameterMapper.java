package pl.com.app.mappers;

import org.mapstruct.Mapper;
import pl.com.app.dto.DietRateParameterDTO;
import pl.com.app.model.DietRateParameter;

@Mapper(componentModel = "spring")
public interface DietRateParameterMapper {
    DietRateParameterDTO dietRateParameterToDietRateParameterDTO(DietRateParameter dietRateParameter);
    DietRateParameter dietRateParameterDTOToDietRateParameter(DietRateParameterDTO dietRateParameterDTO);
}
