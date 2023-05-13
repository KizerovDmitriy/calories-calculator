package com.kizerov.caloriescalculator.service.mapper;

import com.kizerov.caloriescalculator.model.Meal;
import com.kizerov.caloriescalculator.model.MealDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EatenCaloriesMapper {

    Meal toEntity (MealDto mealDto);

    MealDto toDto (Meal meal);

}
