package pl.com.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.app.authentication.AuthenticationFacade;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.dto.MealPlanDTO;
import pl.com.app.dto.RecipeByParameterDTO;
import pl.com.app.model.RecipeByParameter;
import pl.com.app.rest.ResponseMessage;
import pl.com.app.service.RecipeByParameterService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe-by-parameters")
public class RecipeByParameterController {

    private final RecipeByParameterService recipeByParameterService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/user-recipes")
    public ResponseEntity<ResponseMessage<Set<RecipeByParameterDTO>>> findUserRecipes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(null)
                .body(ResponseMessage.<Set<RecipeByParameterDTO>>builder().data(recipeByParameterService.findUserRecipes(authenticationFacade.getLoggedUser())).build());
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseMessage<Set<RecipeByParameterDTO>>> addUserRecipes() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(null)
                .body(ResponseMessage.<Set<RecipeByParameterDTO>>builder().data(recipeByParameterService.addUserRecipes(authenticationFacade.getLoggedUser())).build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage<Set<RecipeByParameterDTO>>> deleteAllUserRecipes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(null)
                .body(ResponseMessage.<Set<RecipeByParameterDTO>>builder().data(recipeByParameterService.deleteAllUserRecipes(authenticationFacade.getLoggedUser())).build());
    }
}
