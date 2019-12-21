package pl.com.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.com.app.dto.InfoDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.model.User;
import pl.com.app.model.enums.Gender;
import pl.com.app.service.RegistrationService;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("registration user test")
    public void test1() throws Exception {
        var u1 = UserDTO.builder().name("A").surname("AA").userName("a").password("aa").email("mail@test.pl").birthday(LocalDate.now()).gender(Gender.MALE).build();


        Mockito
                .when(registrationService.registerNewUser(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(InfoDTO.builder().info(u1.getUserName() + " has been registered").build());

        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/registration/new")
                                .content(toJson(u1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(200, resultActions.andReturn().getResponse().getStatus());
    }

    private static String toJson(UserDTO userDTO) {
        try {
            return new ObjectMapper().writeValueAsString(userDTO);
        } catch (Exception e) {
            throw new IllegalStateException("....");
        }
    }
}
