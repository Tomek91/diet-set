package pl.com.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.app.authentication.AuthenticationFacade;
import pl.com.app.dto.CaloricDemandDTO;
import pl.com.app.rest.ResponseMessage;
import pl.com.app.service.CaloricDemandService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caloric-demand")
public class CaloricDemandController {

    private final CaloricDemandService caloricDemandService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<CaloricDemandDTO>> findOneCaloricDemand(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<CaloricDemandDTO>builder().data(caloricDemandService.findOneCaloricDemand(id)).build());
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseMessage<CaloricDemandDTO>> addCaloricDemand(@Valid @RequestBody CaloricDemandDTO caloricDemandDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.<CaloricDemandDTO>builder().data(caloricDemandService.addCaloricDemand(caloricDemandDTO, authenticationFacade.getLoggedUser())).build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage<CaloricDemandDTO>> updateCaloricDemand(@PathVariable Long id,
                                                                                 @Valid @RequestBody CaloricDemandDTO caloricDemandDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<CaloricDemandDTO>builder().data(caloricDemandService.updateCaloricDemand(id, caloricDemandDTO)).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<CaloricDemandDTO>> deleteCaloricDemand(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseMessage.<CaloricDemandDTO>builder().data(caloricDemandService.deleteCaloricDemand(id)).build());
    }
}
