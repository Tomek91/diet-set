package pl.com.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.app.authentication.AuthenticationFacade;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.dto.MealPlanDTO;
import pl.com.app.rest.ResponseMessage;
import pl.com.app.service.CaloricDemandService;
import pl.com.app.service.MealPlanService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meal-plan")
public class MealPlanController {

    private final MealPlanService mealPlanService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<MealPlanDTO>> findOneMealPlan(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<MealPlanDTO>builder().data(mealPlanService.findOneMealPlan(id)).build());
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseMessage<MealPlanDTO>> addMealPlan() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.<MealPlanDTO>builder().data(mealPlanService.addMealPlan(authenticationFacade.getLoggedUser())).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<MealPlanDTO>> deleteMealPlan(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<MealPlanDTO>builder().data(mealPlanService.deleteMealPlan(id)).build());
    }
}
