package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface IUserService {

    User registerNewUserAccount(UserDto userDto);

    User findUserByEmail(String email);

    List<User> getAll();

    void deleteUser(Long id);

    void adminStatus(Long id);

    void authWithHttpServletRequest(HttpServletRequest request, String userEmail, String password);

}
