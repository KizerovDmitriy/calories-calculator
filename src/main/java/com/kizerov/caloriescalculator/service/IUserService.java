package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;

import java.util.List;

public interface IUserService {

    User registerNewUserAccount(UserDto userDto);
    User findUserByEmail(String email);

    List<User> getAll();

}
