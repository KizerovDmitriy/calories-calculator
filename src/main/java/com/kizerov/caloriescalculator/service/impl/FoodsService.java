package com.kizerov.caloriescalculator.service.impl;

import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.FoodsDto;
import com.kizerov.caloriescalculator.repository.FoodsRepository;
import com.kizerov.caloriescalculator.service.IFoodsService;
import com.kizerov.caloriescalculator.service.mapper.MealMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodsService implements IFoodsService {

    private final FoodsRepository foodsRepository;
    private final MealMapper mealMapper;

    @Override
    public Food addNewFoods(FoodsDto foodsDto) {

        return foodsRepository.save(mealMapper.toEntity(foodsDto));
    }

    @Override
    public void updateFoods(String name, FoodsDto foodsDto) {

        Food food = foodsRepository.findProductByProductName(name)
                .orElseThrow(() -> new EntityNotFoundException("Foods not found"));

        food.setCaloriesPer100Gram(foodsDto.getCaloriesPer100Gram());
        food.setCarbohydrates(foodsDto.getCarbohydrates());
        food.setProtein(foodsDto.getProtein());
        food.setFat(foodsDto.getFat());

        foodsRepository.save(food);

    }


    @Override
    public void deleteFoods(String productName) {

        foodsRepository.deleteProductByProductName(productName);

    }

    @Override
    public List<Food> getAllFoods() {

        return foodsRepository.findAll();
    }

    @Override
    public Food findFoodsByName(String productName){

        return foodsRepository.findProductByProductName(productName).orElseThrow(() -> new EntityNotFoundException("Foods not found"));
    }

}