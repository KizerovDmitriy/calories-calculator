package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.DietaryGoals;
import com.kizerov.caloriescalculator.model.Sex;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NutriCalc {

    private final UserService userService;

    public DietaryGoals dailyGoalCalories(String email) {

        User user = userService.findUserByEmail(email);

        int caloriesNorm;
        int proteinsNorm = (int) (user.getWeight() * 0.8);
        int fatsNorm;
        int carbohydratesNorm;

        if (user.getSex().equals(Sex.FEMALE)) {

            caloriesNorm = (int) (655.1 + (9.563 * user.getWeight()) + (1.85 * user.getHeight()) - (4.676 * user.getAge()));
        } else {

            caloriesNorm = (int) (66.5 + (13.75 * user.getWeight()) + (5.003 * user.getHeight()) - (6.775 * user.getAge()));
        }

        fatsNorm = (int) (caloriesNorm * 0.25 / 9);
        carbohydratesNorm = (int) (caloriesNorm * 0.5 / 4);

        return DietaryGoals.builder()
                .calories(caloriesNorm)
                .proteins(proteinsNorm)
                .fats(fatsNorm)
                .carbohydrates(carbohydratesNorm)
                .build();
    }

}