package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.app.dto.InfoDTO;
import pl.com.app.dto.RoleDTO;
import pl.com.app.dto.UserDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.RoleMapper;
import pl.com.app.mappers.UserMapper;
import pl.com.app.parsers.json.RolesConverter;
import pl.com.app.parsers.json.UsersConverter;
import pl.com.app.repository.RoleRepository;
import pl.com.app.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
//@Transactional
@RequiredArgsConstructor
public class DataInitializerService {
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UsersConverter usersConverter;
    private final RolesConverter rolesConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public InfoDTO initData() {
        try {
            userRepository.deleteAll();
            roleRepository.deleteAll();

            List<UserDTO> userDTOList = usersConverter.fromJson().orElseThrow(NullPointerException::new);
            List<RoleDTO> roleDTOList = rolesConverter.fromJson().orElseThrow(NullPointerException::new);


            roleRepository.saveAll(
                    roleDTOList
                            .stream()
                            .map(roleMapper::roleDTOToRole)
                            .collect(Collectors.toList())
            );

            userRepository.saveAll(
                    userDTOList
                            .stream()
                            .map(userMapper::userDTOToUser)
                            .peek(x -> x.setPassword(passwordEncoder.encode(x.getPassword())))
                            .peek(x -> x.setRole(roleRepository.findByName(x.getRole().getName()).orElseThrow(() -> new NullPointerException("DATA INITIALIZER: FIND ROLE BY NAME EXCEPTION"))))
                            .collect(Collectors.toList())
            );

            return InfoDTO.builder().info("Init data OK.").build();

        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
