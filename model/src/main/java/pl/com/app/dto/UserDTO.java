package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String userName;
    private String password;
    private String passwordConfirmation;
    private LocalDate birthday;
    private String email;
    private Boolean active;
    private Gender gender;
    private RoleDTO roleDTO;
}
