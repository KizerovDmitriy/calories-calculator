package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.CaloriesSummaryDto;
import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.Meal;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CalculatorService implements ICalcService {

    private final MealRepository mealRepository;
    private final UserService userService;
    private final FoodsService foodsService;

    public CaloriesSummaryDto showStatistics(String email, LocalDate startDate, LocalDate endDate) {

        List<Meal> result = mealRepository.findAllByUserEmailAndDate(email, startDate, endDate)
                .orElse(Collections.emptyList());

        return CaloriesSummaryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalCalories(result.stream()
                        .mapToInt(eatenCalories ->
                                eatenCalories.getWeight() * eatenCalories.getFood().getCaloriesPer100Gram() / 100)
                        .sum())
                .meals(result)
                .build();
    }

    public void addTodayMeal(String userEmail, String productName, int weight) {

        User user = userService.findUserByEmail(userEmail);
        Food food = foodsService.findFoodsByName(productName);
        LocalDate today = LocalDate.now();

        Meal meal = mealRepository.findByUserEmailAndProductName(userEmail, productName, today)
                .orElseGet(() -> Meal.builder()
                        .user(user)
                        .food(food)
                        .date(today)
                        .build());

        meal.setWeight(meal.getWeight() + weight);

        mealRepository.save(meal);

    }

}