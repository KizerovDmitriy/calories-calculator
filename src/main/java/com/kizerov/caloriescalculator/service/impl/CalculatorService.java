package com.kizerov.caloriescalculator.service.impl;

import com.kizerov.caloriescalculator.model.CaloriesSummaryDto;
import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.Meal;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.repository.MealRepository;
import com.kizerov.caloriescalculator.service.ICalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalculatorService implements ICalcService {

    private final MealRepository mealRepository;
    private final UserService userService;
    private final FoodsService foodsService;

    @Override
    public CaloriesSummaryDto showStatistics(String email) {
        return showStatistics(email, null, null);
    }

    @Override
    public CaloriesSummaryDto showStatistics(String email, LocalDate startDate, LocalDate endDate) {
        List<Meal> result = Collections.emptyList();

        if (email != null && !email.isEmpty()) {
            if (startDate != null && endDate != null) {
                result = mealRepository.findAllByUserEmailAndDate(email, startDate, endDate)
                        .orElse(Collections.emptyList());
            } else {
                result = mealRepository.findAllByUserEmail(email)
                        .orElse(Collections.emptyList());
            }
        }

        result = result.stream()
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());

        int totalCalories = result.stream()
                .mapToInt(eatenCalories ->
                        eatenCalories.getWeight() * eatenCalories.getFood().getCaloriesPer100Gram() / 100)
                .sum();

        int totalProteins = result.stream()
                .mapToInt(eatenCalories ->
                        eatenCalories.getWeight() * eatenCalories.getFood().getProtein() / 100)
                .sum();

        int totalFats = result.stream()
                .mapToInt(eatenCalories ->
                        eatenCalories.getWeight() * eatenCalories.getFood().getFat() / 100)
                .sum();

        int totalCarbohydrates = result.stream()
                .mapToInt(eatenCalories ->
                        eatenCalories.getWeight() * eatenCalories.getFood().getCarbohydrates() / 100)
                .sum();

        return CaloriesSummaryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalCalories(totalCalories)
                .totalProteins(totalProteins)
                .totalFats(totalFats)
                .totalCarbohydrates(totalCarbohydrates)
                .meals(result)
                .build();
    }

    @Override
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