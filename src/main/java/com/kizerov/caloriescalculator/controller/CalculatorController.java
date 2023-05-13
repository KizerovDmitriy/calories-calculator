package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.MealDto;
import com.kizerov.caloriescalculator.service.CalculatorService;
import com.kizerov.caloriescalculator.service.FoodsService;
import com.kizerov.caloriescalculator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/calc")
@RequiredArgsConstructor
public class CalculatorController {

    private final FoodsService productService;
    private final CalculatorService calculatorService;
    private final UserService userService;

    @GetMapping("/statistics")
    public String getCaloriesIntakeSummary(Model model, @RequestParam("email") String email,
                                           @RequestParam("start-date") LocalDate startDate, @RequestParam("end-date") LocalDate endDate) {

        model.addAttribute("user", userService.findUserByEmail(email));
        model.addAttribute("caloriesSummaryStatistic", calculatorService.showStatistics(email,startDate,endDate));

        return "statistics";

    }

    @GetMapping()
    public String showCalcPage(Model model) {

        model.addAttribute("productsList", productService.getAllFoods());

        return "calc";

    }

    @PostMapping("/meals")
    public String addTodayMeal(@ModelAttribute("productsList") @Valid MealDto mealDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "calc";
        }

        calculatorService.addTodayMeal(mealDto.getEmail(), mealDto.getProductName(), mealDto.getWeight());

        return "redirect:/calc";

    }

}
