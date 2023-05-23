package com.kizerov.caloriescalculator.configuration;

import com.kizerov.caloriescalculator.model.Role;
import com.kizerov.caloriescalculator.service.impl.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                    .requestMatchers("/admin/**")
                    .hasAuthority(Role.ADMIN.name())
                    .requestMatchers("/","/registration")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .authenticationProvider(authenticationProvider(myUserDetailsService));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailsService) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

}