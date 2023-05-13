package com.kizerov.caloriescalculator.service.mapper;

import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.FoodsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodsMapper {

    Food toEntity(FoodsDto foodsDto);
    FoodsDto toDto(Food food);

}
