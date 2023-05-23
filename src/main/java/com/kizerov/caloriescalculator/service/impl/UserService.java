package com.kizerov.caloriescalculator.service.impl;

import com.kizerov.caloriescalculator.exception.RegistrationException;
import com.kizerov.caloriescalculator.model.Role;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import com.kizerov.caloriescalculator.repository.UserRepository;
import com.kizerov.caloriescalculator.service.IUserService;
import com.kizerov.caloriescalculator.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    @Override
    public void adminStatus(Long id) {

        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        });
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }

    @Transactional
    @Override
    public List<User> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "email");
        return userRepository.findAll(sort);
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto userDto) throws RegistrationException {

        validation(userDto);

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
    }

    private void validation(UserDto userDto) {

        if (emailExists(userDto.getEmail()))
            throw new RegistrationException("Пользователь с таким " + userDto.getEmail() + " уже зарегистрирован");

        if (!userDto.getEmail().contains("@"))
            throw new RegistrationException("Incorrect email address");

    }

    private boolean emailExists(String email) {

        return userRepository.findUserByEmail(email).isPresent();
    }

}