package pl.com.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.app.mappers.UserMapper;
import pl.com.app.model.User;
import pl.com.app.model.enums.Gender;
import pl.com.app.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @TestConfiguration
    public static class MyTestConfiguration {


        private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

        @MockBean
        private UserRepository userRepository;

        @Bean
        public UserService userService() {
            return new UserService(userRepository, userMapper);
        }

    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    @DisplayName("find by mail user test")
    public void test1() {
        var email = "mail@test.pl";
        var u1 = User.builder().name("A").surname("AA").userName("a").password("aa").email("mail@test.pl").birthday(LocalDate.now()).gender(Gender.MALE).build();

        Mockito
                .when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(u1));

        var userByMail = userService.findByEmail(email);

        Assertions.assertTrue(userByMail.isPresent());
        Assertions.assertEquals("A", userByMail.get().getName());
        Assertions.assertEquals("AA", userByMail.get().getSurname());
    }

    @Test
    @DisplayName("find by username user test")
    public void test2() {
        var username = "a";
        var u1 = User.builder().name("A").surname("AA").userName("a").password("aa").email("mail@test.pl").birthday(LocalDate.now()).gender(Gender.MALE).build();

        Mockito
                .when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(u1));

        var userByUserName = userService.findByUserName(username);

        Assertions.assertTrue(userByUserName.isPresent());
        Assertions.assertEquals("A", userByUserName.get().getName());
        Assertions.assertEquals("AA", userByUserName.get().getSurname());
    }
}
