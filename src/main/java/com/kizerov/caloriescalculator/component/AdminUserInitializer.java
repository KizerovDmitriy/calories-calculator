package com.kizerov.caloriescalculator.component;

import com.kizerov.caloriescalculator.model.Role;
import com.kizerov.caloriescalculator.model.Sex;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.count() == 0) {

            User adminUser = new User();
            adminUser.setEmail("kizerov.dmitriy@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("atybrclbvf"));
            adminUser.setRole(Role.ADMIN);
            adminUser.setSex(Sex.MALE);

            userRepository.save(adminUser);

        }

    }

}