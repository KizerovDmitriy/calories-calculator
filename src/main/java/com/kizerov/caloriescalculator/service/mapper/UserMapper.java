package com.kizerov.caloriescalculator.service.mapper;

import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDTO(User user);
    User toEntity(UserDto userDTO);

}
