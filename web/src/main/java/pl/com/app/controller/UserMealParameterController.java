package pl.com.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.app.authentication.AuthenticationFacade;
import pl.com.app.dto.UserMealParameterDTO;
import pl.com.app.rest.ResponseMessage;
import pl.com.app.service.UserMealParameterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-meal-parameter")
public class UserMealParameterController {

    private final UserMealParameterService userMealParameterService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<UserMealParameterDTO>> findOneUserMealParameter(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(null)
                .body(ResponseMessage.<UserMealParameterDTO>builder().data(userMealParameterService.findOneUserMealParameter(id)).build());
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseMessage<UserMealParameterDTO>> addUserMealParameter(@Valid @RequestBody UserMealParameterDTO userMealParameterDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(null)
                .body(ResponseMessage.<UserMealParameterDTO>builder().data(userMealParameterService.addUserMealParameter(userMealParameterDTO, authenticationFacade.getLoggedUser())).build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage<UserMealParameterDTO>> updateUserMealParameter(@PathVariable Long id,
                                                                                 @Valid @RequestBody UserMealParameterDTO userMealParameterDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(null)
                .body(ResponseMessage.<UserMealParameterDTO>builder().data(userMealParameterService.updateUserMealParameter(id, userMealParameterDTO)).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<UserMealParameterDTO>> deleteUserMealParameter(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(null)
                .body(ResponseMessage.<UserMealParameterDTO>builder().data(userMealParameterService.deleteUserMealParameter(id)).build());
    }
}
