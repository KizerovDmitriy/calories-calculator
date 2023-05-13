package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.exception.RegistrationException;
import com.kizerov.caloriescalculator.model.Role;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import com.kizerov.caloriescalculator.repository.UserRepository;
import com.kizerov.caloriescalculator.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<User> getAll() {

        return userRepository.findAll();

    }
    @Transactional
    public User registerNewUserAccount(UserDto userDto) throws RegistrationException {

        validation(userDto);

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);

    }

    @Transactional
    public User findUserByEmail(String email){

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

    }

    private void validation(UserDto userDto) {

        if (emailExists(userDto.getEmail()))
            throw new RegistrationException("There is an account with that email address: "
                    + userDto.getEmail());

        if (!userDto.getEmail().contains("@"))
            throw new RegistrationException("Incorrect email address");

    }

    private boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

}