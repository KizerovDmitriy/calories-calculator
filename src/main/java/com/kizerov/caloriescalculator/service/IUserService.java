package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.Sex;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface IUserService {

    void registerNewUserAccount(UserDto userDto);

    User findUserByEmail(String email);

    List<User> getAll();

    void updateUserFields(Long id, Sex sex, int age, int height, int weight);

    void deleteUser(Long id);

    void adminStatus(Long id);

    void authWithHttpServletRequest(HttpServletRequest request, String userEmail, String password);

    boolean isProfileIncomplete(String email);

}
