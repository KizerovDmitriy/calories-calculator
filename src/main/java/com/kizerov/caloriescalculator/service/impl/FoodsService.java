package com.kizerov.caloriescalculator.service.impl;

import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.FoodsDto;
import com.kizerov.caloriescalculator.repository.FoodsRepository;
import com.kizerov.caloriescalculator.service.IFoodsService;
import com.kizerov.caloriescalculator.service.mapper.MealMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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

        String foodName = foodsDto.getProductName();

        if (Character.isDigit(foodName.charAt(0)) || !Character.isLetter(foodName.charAt(0))) {

            throw new IllegalArgumentException("Назва продукту містить недопустимі символи.");
        }

        if (foodName.matches("^[\\d\\s]+$")) {

            throw new IllegalArgumentException("Назва продукту не може кладатись тільки з цифр.");
        }

        if (foodName.length() < 3) {

            throw new IllegalArgumentException("Довжина слова повинна бути не менше трьох букв.");
        }

        if (foodsRepository.findProductByProductName(foodName).isPresent()) {

            throw new DuplicateKeyException(foodName + " вже є в базі даних");
        }

        if (foodsDto.getCaloriesPer100Gram() <= 0 || foodsDto.getProtein() < 0
                || foodsDto.getFat() < 0 || foodsDto.getCarbohydrates() < 0) {

            throw new IllegalArgumentException("Значення калорій або БЖУ не може бути від'ємним або дорівнювати нулю.");
        }

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
    public void updateFoods(Food food) {

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
    public Food findFoodsByName(String productName) {

        return foodsRepository.findProductByProductName(productName).orElseThrow(() -> new EntityNotFoundException("Foods not found"));
    }

    @Override
    public Food findFoodById(Long id) {

        return foodsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт за таким ID - " + id + " не знайденно"));
    }

}