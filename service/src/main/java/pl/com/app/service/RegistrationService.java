package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.InfoDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.UserMapper;
import pl.com.app.model.Role;
import pl.com.app.model.User;
import pl.com.app.model.VerificationToken;
import pl.com.app.repository.RoleRepository;
import pl.com.app.repository.UserRepository;
import pl.com.app.repository.VerificationTokenRepository;
import pl.com.app.service.listeners.OnRegistrationEvenData;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;


    public void createVerificationToken(User user, String token) {
            if (user == null) {
                throw new MyException(ExceptionCode.SERVICE, "USER IS NULL");
            }

            if (token == null) {
                throw new MyException(ExceptionCode.SERVICE, "TOKEN IS NULL");
            }

            VerificationToken verificationToken = new VerificationToken();
            Optional<VerificationToken> verificationTokenOpt = verificationTokenRepository.findByUserId_Equals(user.getId());
            if (verificationTokenOpt.isPresent()){
                verificationToken = verificationTokenOpt.get();
            }

            verificationToken.setUser(userRepository.getOne(user.getId()));
            verificationToken.setToken(token);
            verificationToken.setExpirationDateTime(LocalDateTime.now().plusDays(1L));

            verificationTokenRepository.save(verificationToken);
    }

    public InfoDTO confirmRegistration(String token) {
            if (token == null) {
                throw new MyException(ExceptionCode.SERVICE, "TOKEN IS NULL");
            }

            VerificationToken verificationToken
                    = verificationTokenRepository.findByToken(token)
                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "USER WITH TOKEN " + token + " DOESN'T EXIST"));

            if (verificationToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
                throw new MyException(ExceptionCode.SERVICE, "TOKEN HAS BEEN EXPIRED");
            }

            User user = verificationToken.getUser();
            user.setActive(true);
            userRepository.save(user);

            verificationToken.setToken(null);
            verificationTokenRepository.save(verificationToken);

            return InfoDTO.builder().info(user.getUserName() + " registration confirmation correctly.").build();
    }

    public InfoDTO registerNewUser(UserDTO userDTO, HttpServletRequest request) {
            if (userDTO == null) {
                throw new MyException(ExceptionCode.SERVICE, "USER OBJECT IS NULL");
            }
            if (userDTO.getRoleDTO() == null) {
                throw new MyException(ExceptionCode.SERVICE, "ROLE OBJECT IS NULL");
            }
            if (userDTO.getRoleDTO().getName() == null) {
                throw new MyException(ExceptionCode.SERVICE, "ROLE NAME IS NULL");
            }

            Role role = roleRepository
                    .findByName(userDTO.getRoleDTO().getName())
                    .orElseThrow(NullPointerException::new);

            User user = userMapper.userDTOToUser(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(role);
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setUserName(userDTO.getUserName());
            user.setActive(false);
            userRepository.save(user);

            final String url = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationEvenData(url, user));

            return InfoDTO.builder().info(user.getUserName() + " has been registered").build();
    }
}
