package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.dto.DietRateParameterDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.dto.UserMealParameterDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.CaloricDemandMapper;
import pl.com.app.mappers.UserMealParameterMapper;
import pl.com.app.model.CaloricDemand;
import pl.com.app.model.DietRateParameter;
import pl.com.app.model.User;
import pl.com.app.model.UserMealParameter;
import pl.com.app.model.enums.DietType;
import pl.com.app.model.enums.Gender;
import pl.com.app.repository.CaloricDemandRepository;
import pl.com.app.repository.DietRateParameterRepository;
import pl.com.app.repository.UserMealParameterRepository;
import pl.com.app.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserMealParameterService {
    private final UserMealParameterRepository userMealParameterRepository;
    private final UserMealParameterMapper userMealParameterMapper;
    private final UserRepository userRepository;


    public UserMealParameterDTO findOneUserMealParameter(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER MEAL PARAMETER ID IS NULL");
        }
        UserMealParameter userMealParameter = userMealParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user meal parameter with id " + id + " doesn't exist"));
        return userMealParameterMapper.userMealParameterToUserMealParameterDTO(userMealParameter);
    }

    @Transactional
    public UserMealParameterDTO addUserMealParameter(UserMealParameterDTO userMealParameterDTO, UserDTO loggedUser) {
        if (userMealParameterDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER MEAL PARAMETER IS NULL");
        }

        if (loggedUser == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER DTO IS NULL");
        }

        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user with id " + loggedUser.getId() + " doesn't exist"));

        UserMealParameter userMealParameterToSave = userMealParameterMapper.userMealParameterDTOToUserMealParameter(userMealParameterDTO);
        userMealParameterToSave.setUser(user);
        UserMealParameter userMealParameterFromDb = userMealParameterRepository.save(userMealParameterToSave);
        return userMealParameterMapper.userMealParameterToUserMealParameterDTO(userMealParameterFromDb);
    }

    @Transactional
    public UserMealParameterDTO updateUserMealParameter(Long id, UserMealParameterDTO userMealParameterDTO) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER MEAL PARAMETER ID IS NULL");
        }
        if (userMealParameterDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER MEAL PARAMETER IS NULL");
        }
        UserMealParameter userMealParameter = userMealParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user meal parameter with id " + id + " doesn't exist"));

        userMealParameter.setCuisines(userMealParameterDTO.getCuisines() == null ? userMealParameter.getCuisines() : userMealParameterDTO.getCuisines());
        userMealParameter.setDiet(userMealParameterDTO.getDiet() == null ? userMealParameter.getDiet() : userMealParameterDTO.getDiet());
        userMealParameter.setExcludeCuisines(userMealParameterDTO.getExcludeCuisines() == null ? userMealParameter.getExcludeCuisines() : userMealParameterDTO.getExcludeCuisines());
        userMealParameter.setExcludeIngredients(userMealParameterDTO.getExcludeIngredients() == null ? userMealParameter.getExcludeIngredients() : userMealParameterDTO.getExcludeIngredients());
        userMealParameter.setIncludeIngredients(userMealParameterDTO.getIncludeIngredients() == null ? userMealParameter.getIncludeIngredients() : userMealParameterDTO.getIncludeIngredients());

        UserMealParameter userMealParameterFromDb = userMealParameterRepository.save(userMealParameter);
        return userMealParameterMapper.userMealParameterToUserMealParameterDTO(userMealParameterFromDb);
    }

    @Transactional
    public UserMealParameterDTO deleteUserMealParameter(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER MEAL PARAMETER ID IS NULL");
        }
        UserMealParameter userMealParameter = userMealParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user meal parameter with id " + id + " doesn't exist"));
        userMealParameterRepository.delete(userMealParameter);
        return userMealParameterMapper.userMealParameterToUserMealParameterDTO(userMealParameter);
    }
}
