package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.RecipeByParameterDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.RecipeByParameterMapper;
import pl.com.app.model.CaloricDemand;
import pl.com.app.model.RecipeByParameter;
import pl.com.app.model.User;
import pl.com.app.model.UserMealParameter;
import pl.com.app.repository.CaloricDemandRepository;
import pl.com.app.repository.RecipeByParameterRepository;
import pl.com.app.repository.UserMealParameterRepository;
import pl.com.app.repository.UserRepository;
import pl.com.app.service.spooncacular.mapper.MealByParametersMapper;
import pl.com.app.service.spooncacular.model.MealByParameters;
import pl.com.app.service.spooncacular.service.SpooncacularService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeByParameterService {

    private final RecipeByParameterRepository recipeByParameterRepository;
    private final RecipeByParameterMapper recipeByParameterMapper;
    private final CaloricDemandRepository caloricDemandRepository;
    private final UserMealParameterRepository userMealParameterRepository;
    private final UserRepository userRepository;
    private final SpooncacularService spooncacularService;


    public Set<RecipeByParameterDTO> addUserRecipes(UserDTO loggedUser) {
        if (loggedUser == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER IS NULL");
        }
        if (loggedUser.getId() == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER ID IS NULL");
        }
        CaloricDemand caloricDemand = caloricDemandRepository
                .findByUser_Id(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "user id " + loggedUser.getId() + " does not have caloric demand"));

        UserMealParameter userMealParameter = userMealParameterRepository
                .findByUser_Id(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "user id " + loggedUser.getId() + " does not have meal parameter"));

        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user with id " + loggedUser.getId() + " doesn't exist"));

        MealByParameters recipeByParameters = spooncacularService.findRecipeByParameters(caloricDemand, userMealParameter);
        Set<RecipeByParameter> recipeByParametersToSave = MealByParametersMapper.mealByParametersToRecipeByParameterCollection(recipeByParameters);
        return recipeByParametersToSave.stream()
                .peek(x -> x.setUser(user))
                .map(recipeByParameterRepository::save)
                .map(recipeByParameterMapper::recipeByParameterToRecipeByParameterDTO)
                .collect(Collectors.toSet());
    }

    public Set<RecipeByParameterDTO> findUserRecipes(UserDTO loggedUser) {
        if (loggedUser == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER IS NULL");
        }
        if (loggedUser.getId() == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER ID IS NULL");
        }
        return recipeByParameterRepository
                .findAllByUser_Id(loggedUser.getId())
                .stream()
                .map(recipeByParameterMapper::recipeByParameterToRecipeByParameterDTO)
                .collect(Collectors.toSet());
    }

    public Set<RecipeByParameterDTO> deleteAllUserRecipes(UserDTO loggedUser) {
        if (loggedUser == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER IS NULL");
        }
        if (loggedUser.getId() == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER ID IS NULL");
        }
        Set<RecipeByParameter> userRecipies = recipeByParameterRepository
                .findAllByUser_Id(loggedUser.getId());

        recipeByParameterRepository.deleteAll(userRecipies);

        return userRecipies
                .stream()
                .map(recipeByParameterMapper::recipeByParameterToRecipeByParameterDTO)
                .collect(Collectors.toSet());
    }
}
