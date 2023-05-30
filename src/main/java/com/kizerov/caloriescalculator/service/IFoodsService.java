package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.FoodsDto;

import java.util.List;

public interface IFoodsService {

    Food addNewFoods(FoodsDto foodsDto);

    void updateFoods(String name, FoodsDto foodsDto);

    void updateFoods(Food food);

    void deleteFoods(String productName);

    List<Food> getAllFoods();

    Food findFoodsByName(String productName);

    Food findFoodById(Long id);

}