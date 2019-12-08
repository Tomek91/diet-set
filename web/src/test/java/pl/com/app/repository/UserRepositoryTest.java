package pl.com.app.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.com.app.model.User;
import pl.com.app.model.enums.Gender;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:applicationtest.properties")
public class UserRepositoryTest {

    @TestConfiguration
    public static class ContextConfiguration {
        @MockBean
        public JavaMailSender mailSender;
    }

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("add users test")
    void test1() {
        var p1 = User.builder().name("A").surname("AA").userName("a").password("aa").birthday(LocalDate.now()).gender(Gender.MALE).build();
        var p2 = User.builder().name("B").surname("BB").userName("b").password("bb").birthday(LocalDate.now()).gender(Gender.FEMALE).build();
        testEntityManager.persist(p1);
        testEntityManager.persist(p2);
        testEntityManager.flush();

        Assertions.assertEquals(2, userRepository.findAll().size());
    }
}
