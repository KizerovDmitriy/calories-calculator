package com.kizerov.caloriescalculator.service.impl;

import com.kizerov.caloriescalculator.exception.RegistrationException;
import com.kizerov.caloriescalculator.model.Role;
import com.kizerov.caloriescalculator.model.Sex;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import com.kizerov.caloriescalculator.repository.UserRepository;
import com.kizerov.caloriescalculator.service.IUserService;
import com.kizerov.caloriescalculator.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
    public void updateUserFields(Long id, Sex sex, int age, int height, int weight) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Користувача з таким айди: " + id + " не знайденно"));

        user.setSex(sex);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);

        userRepository.save(user);
    }


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
    public void registerNewUserAccount(UserDto userDto) throws RegistrationException {

        validation(userDto);
        isPasswordValid(userDto.getPassword());

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача з таким емелом " + email + " не знайдено"));
    }

    @Override
    public void authWithHttpServletRequest(HttpServletRequest request, String userEmail, String password) {
        try {
            request.login(userEmail, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void validation(UserDto userDto) {

        if (emailExists(userDto.getEmail()))
            throw new RegistrationException("Користувач з таким емейлом " + userDto.getEmail() + " вже зараєстрований");

        if (!userDto.getEmail().contains("@"))
            throw new RegistrationException("Не вірний формат емейлу");

    }

    private boolean emailExists(String email) {

        return userRepository.findUserByEmail(email).isPresent();
    }

    private void isPasswordValid(String password) {

        if (password.length() < 6) {

            throw new RegistrationException("Пароль повинен бути не меньше 6 символів.");
        }

        if (!password.matches(".*\\d.*")) {

            throw new RegistrationException("В паролі повинна бути хоча б одна цифра.");
        }

    }

    @Override
    public boolean isProfileIncomplete(String email) {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Користувача з таким емейлом: " + email + " не знайденно."));

        return user.getAge() <= 0 || user.getHeight() <= 0 || user.getWeight() <= 0;
    }

}