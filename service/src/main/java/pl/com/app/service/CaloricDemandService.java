package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.CaloricDemandMapper;
import pl.com.app.model.CaloricDemand;
import pl.com.app.model.DietRateParameter;
import pl.com.app.model.User;
import pl.com.app.model.enums.DietType;
import pl.com.app.model.enums.Gender;
import pl.com.app.repository.CaloricDemandRepository;
import pl.com.app.repository.DietRateParameterRepository;
import pl.com.app.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaloricDemandService {
    private final CaloricDemandRepository caloricDemandRepository;
    private final DietRateParameterRepository dietRateParameterRepository;
    private final UserRepository userRepository;
    private final CaloricDemandMapper caloricDemandMapper;

    @Transactional
    public CaloricDemandDTO addCaloricDemand(CaloricDemandDTO caloricDemandDTO, UserDTO userDTO) {
        if (caloricDemandDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALORIC DEMAND DTO IS NULL");
        }

        if (userDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER DTO IS NULL");
        }

        User user = userRepository
                .findById(userDTO.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user with id " + userDTO.getId() + " doesn't exist"));

        CaloricDemand caloricDemandToSave = caloricDemandMapper.caloricDemandDTOToCaloricDemand(caloricDemandDTO);
        caloricDemandToSave.setUser(user);
        Integer nominalValue = calculateNominalValue(user, caloricDemandDTO);
        caloricDemandToSave.setNominalValue(nominalValue);
        caloricDemandToSave.setDietValue(calculateDietValue(nominalValue, caloricDemandToSave.getDietType()));
        CaloricDemand caloricDemandFromDb = caloricDemandRepository.save(caloricDemandToSave);
        return caloricDemandMapper.caloricDemandToCaloricDemandDTO(caloricDemandFromDb);
    }

    private Integer calculateNominalValue(User user, CaloricDemandDTO caloricDemandDTO) {
        Double retValue = null;
        if (user.getGender().equals(Gender.MALE)){
            retValue = 66.5 + (13.7 * caloricDemandDTO.getWeight()) + (5 * caloricDemandDTO.getHeight()) - (6.8 * getAge(user.getBirthday()));
        } else {
            retValue = 655 + (9.6 * caloricDemandDTO.getWeight()) + (1.85 * caloricDemandDTO.getHeight()) - (4.7 * getAge(user.getBirthday()));
        }
        return retValue.intValue();
    }

    private int getAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    private Integer calculateDietValue(Integer nominalValue, DietType dietType) {
        if (nominalValue == null) {
            throw new MyException(ExceptionCode.SERVICE, "NOMINAL VALUE IS NULL");
        }

        if (dietType == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET TYPE IS NULL");
        }
        DietRateParameter dietRateParameter = dietRateParameterRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER IS NULL"));

        return Double.valueOf(nominalValue * getRateParameter(dietType.toString(), dietRateParameter)).intValue();
    }

    private Double getRateParameter(String dietType, DietRateParameter dietRateParameter) {
        if (dietType.equals(DietType.FAST_SLIM.toString())){
            return dietRateParameter.getFastSlimValue();
        } else if (dietType.equals(DietType.SLIM.toString())){
            return dietRateParameter.getAverageSlimValue();
        }else if (dietType.equals(DietType.PUT_ON.toString())){
            return dietRateParameter.getAveragePutOnValue();
        }else if (dietType.equals(DietType.FAST_PUT_ON.toString())){
            return dietRateParameter.getFastPutOnValue();
        } else {
            return 1.0;
        }
    }

    public CaloricDemandDTO findOneCaloricDemand(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALORIC DEMAND ID IS NULL");
        }
        CaloricDemand caloricDemandFromDb = caloricDemandRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "caloric demand with id " + id + " doesn't exist"));
        return caloricDemandMapper.caloricDemandToCaloricDemandDTO(caloricDemandFromDb);
    }

    public CaloricDemandDTO updateCaloricDemand(Long id, CaloricDemandDTO caloricDemandDTO) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALORIC DEMAND ID IS NULL");
        }
        if (caloricDemandDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALORIC DEMAND DTO IS NULL");
        }
        CaloricDemand caloricDemandFromDb = caloricDemandRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "caloric demand with id " + id + " doesn't exist"));

        caloricDemandFromDb.setDietType(caloricDemandDTO.getDietType() == null ? caloricDemandFromDb.getDietType() : caloricDemandDTO.getDietType());
        caloricDemandFromDb.setHeight(caloricDemandDTO.getHeight() == null ? caloricDemandFromDb.getHeight() : caloricDemandDTO.getHeight());
        caloricDemandFromDb.setWeight(caloricDemandDTO.getWeight() == null ? caloricDemandFromDb.getWeight() : caloricDemandDTO.getWeight());

        User user = userRepository
                .findById(caloricDemandDTO.getUserDTO().getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user with id " + caloricDemandDTO.getUserDTO().getId() + " doesn't exist"));

        Integer nominalValue = calculateNominalValue(user, caloricDemandDTO);
        caloricDemandFromDb.setNominalValue(nominalValue);
        caloricDemandFromDb.setDietValue(calculateDietValue(nominalValue, caloricDemandFromDb.getDietType()));

        return caloricDemandMapper.caloricDemandToCaloricDemandDTO(caloricDemandFromDb);
    }

    public CaloricDemandDTO deleteCaloricDemand(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "CALORIC DEMAND ID IS NULL");
        }
        CaloricDemand caloricDemandFromDb = caloricDemandRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "caloric demand with id " + id + " doesn't exist"));
        caloricDemandRepository.delete(caloricDemandFromDb);
        return caloricDemandMapper.caloricDemandToCaloricDemandDTO(caloricDemandFromDb);
    }
}
