package pl.com.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.model.CaloricDemand;

@Mapper(componentModel = "spring")
public interface CaloricDemandMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO")
    })
    CaloricDemandDTO caloricDemandToCaloricDemandDTO(CaloricDemand caloricDemand);

    @Mappings({
            @Mapping(source = "userDTO", target = "user")
    })
    CaloricDemand caloricDemandDTOToCaloricDemand(CaloricDemandDTO caloricDemandDTO);
}
