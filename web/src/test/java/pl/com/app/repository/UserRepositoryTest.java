package pl.com.app.repository;

import org.junit.jupiter.api.*;
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
import java.util.Optional;

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

    @BeforeEach
    public void init(){
        var p1 = User.builder().name("A").surname("AA").userName("a").password("aa").email("mail@test.pl").birthday(LocalDate.now()).gender(Gender.MALE).build();
        var p2 = User.builder().name("B").surname("BB").userName("b").password("bb").email("mail2@test.pl").birthday(LocalDate.now()).gender(Gender.FEMALE).build();
        testEntityManager.persist(p1);
        testEntityManager.persist(p2);
        testEntityManager.flush();
    }

    @Test
    @DisplayName("add users test")
    void test1() {
        Assertions.assertEquals(2, userRepository.findAll().size());
    }

    @Test
    @DisplayName("find by mail user test")
    void test2() {
        var mailToFind = "mail@test.pl";
        Optional<User> userByMail = userRepository.findByEmail(mailToFind);
        Assertions.assertTrue(userByMail.isPresent());
        Assertions.assertEquals("A", userByMail.get().getName());
    }

    @Test
    @DisplayName("find by username user test")
    void test3() {
        var userNameToFind = "b";
        var userByUserName = userRepository.findByUserName(userNameToFind);
        Assertions.assertTrue(userByUserName.isPresent());
        Assertions.assertEquals("BB", userByUserName.get().getSurname());
    }

    @Test
    @DisplayName("delete user test")
    void test4() {
        userRepository.deleteAll();
        Assertions.assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("update user test")
    void test5() {
        var mailToFind = "mail@test.pl";
        var name = "new name";
        var surname = "new surname";
        var user = userRepository.findByEmail(mailToFind).get();
        user.setName(name);
        user.setSurname(surname);
        userRepository.saveAndFlush(user);

        var userFromDb = userRepository.findById(user.getId()).get();
        Assertions.assertNotNull(userFromDb);
        Assertions.assertEquals(surname, userFromDb.getSurname());
        Assertions.assertEquals(name, userFromDb.getName());
    }
}
