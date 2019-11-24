package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.dto.InfoDTO;
import pl.com.app.dto.MealPlanDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.MealPlanMapper;
import pl.com.app.mappers.UserMapper;
import pl.com.app.model.*;
import pl.com.app.repository.*;
import pl.com.app.service.spooncacular.mapper.MealByParametersMapper;
import pl.com.app.service.spooncacular.mapper.MealPlanGeneratedMapper;
import pl.com.app.service.spooncacular.model.MealPlanGenerated;
import pl.com.app.service.spooncacular.service.SpooncacularService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final CaloricDemandRepository caloricDemandRepository;
    private final MealPlanMapper mealPlanMapper;
    private final SpooncacularService spooncacularService;
    private final UserRepository userRepository;


    public MealPlanDTO addMealPlan(UserDTO loggedUser) {
        if (loggedUser == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER IS NULL");
        }
        if (loggedUser.getId() == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER ID IS NULL");
        }
        CaloricDemand caloricDemand = caloricDemandRepository
                .findByUser_Id(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "user id " + loggedUser.getId() + " doesn not have caloric demand"));

        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user with id " + loggedUser.getId() + " doesn't exist"));

        MealPlanGenerated mealPlanGenerated = spooncacularService.findMealPlan(caloricDemand);
        MealPlan mealPlanToSave = MealPlanGeneratedMapper.mealPlanGeneratedToMealPlan(mealPlanGenerated);
        mealPlanToSave.setUser(user);
        MealPlan mealPlanFromDb = mealPlanRepository.save(mealPlanToSave);
        return mealPlanMapper.mealPlanToMealPlanDTO(mealPlanFromDb);
    }

    public MealPlanDTO findOneMealPlan(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "MEAL PLAN ID IS NULL");
        }
        MealPlan mealPlan = mealPlanRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "user meal parameter with id " + id + " doesn't exist"));
        return mealPlanMapper.mealPlanToMealPlanDTO(mealPlan);
    }

    public MealPlanDTO deleteMealPlan(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "MEAL PLAN ID IS NULL");
        }
        MealPlan mealPlan = mealPlanRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "meal plan with id " + id + " doesn't exist"));
        mealPlanRepository.delete(mealPlan);
        return mealPlanMapper.mealPlanToMealPlanDTO(mealPlan);
    }
}
