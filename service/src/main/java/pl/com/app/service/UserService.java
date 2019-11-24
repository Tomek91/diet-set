package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.UserMapper;
import pl.com.app.repository.UserRepository;
import pl.com.app.repository.VerificationTokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;

    public Optional<UserDTO> findByUserName(String userName) {
        if (userName == null) {
            throw new MyException(ExceptionCode.SERVICE, "USERNAME IS NULL");
        }
        return userRepository
                .findByUserName(userName)
                .map(userMapper::userToUserDTO);
    }

    public Optional<UserDTO> findByEmail(String email) {
        if (email == null) {
            throw new MyException(ExceptionCode.SERVICE, "EMAIL IS NULL");
        }
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDTO);
    }
}
