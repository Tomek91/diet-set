package pl.com.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.app.dto.DietRateParameterDTO;
import pl.com.app.rest.ResponseMessage;
import pl.com.app.service.DietRateParameterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diet-rate-parameter")
public class DietRateParameterController {

    private final DietRateParameterService dietRateParameterService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<DietRateParameterDTO>> findOneDietRateParameter(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<DietRateParameterDTO>builder().data(dietRateParameterService.findOneDietRateParameter(id)).build());
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage<DietRateParameterDTO>> addDietRateParameter(@Valid @RequestBody DietRateParameterDTO dietRateParameterDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.<DietRateParameterDTO>builder().data(dietRateParameterService.addDietRateParameter(dietRateParameterDTO)).build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage<DietRateParameterDTO>> updateDietRateParameter(@PathVariable Long id,
                                                                                     @Valid @RequestBody DietRateParameterDTO dietRateParameterDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<DietRateParameterDTO>builder().data(dietRateParameterService.updateDietRateParameter(id, dietRateParameterDTO)).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage<DietRateParameterDTO>> deleteDietRateParameter(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<DietRateParameterDTO>builder().data(dietRateParameterService.deleteDietRateParameter(id)).build());
    }
}
