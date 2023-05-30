package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.MealDto;
import com.kizerov.caloriescalculator.service.impl.CalculatorService;
import com.kizerov.caloriescalculator.service.impl.FoodsService;
import com.kizerov.caloriescalculator.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/calories")
@RequiredArgsConstructor
public class CalculatorController {

    private final FoodsService productService;
    private final CalculatorService calculatorService;
    private final UserService userService;

    @GetMapping("/statistics")
    public String getCaloriesIntakeSummary(Model model,
                                           @AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestParam("start-date") LocalDate startDate,
                                           @RequestParam("end-date") LocalDate endDate,
                                           Authentication authentication) {

        model.addAttribute("user", userService.findUserByEmail(userDetails.getUsername()));
        model.addAttribute("caloriesSummaryStatistic", calculatorService.showStatistics(userDetails.getUsername(), startDate, endDate));
        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        if (authentication != null && authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {

            model.addAttribute("adminStatistic", calculatorService.showStatistics(email, startDate, endDate));
            model.addAttribute("userEmail", email);

            return "admin-statistics";
        }

        return "statistics";
    }

    @GetMapping()
    public String showCalcPage(Model model, Authentication authentication) {

        model.addAttribute("productsList", productService.getAllFoods());
        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        if (authentication != null && authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {

            return "calcadmin";
        }

        return "calc";
    }

    @GetMapping("/add-new-foods")
    public String addNewFoods(Model model, Authentication authentication) {

        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "add-new-foods";
    }

    @PostMapping("/meals")
    public String addTodayMeal(@ModelAttribute("productsList") @Valid MealDto mealDto,
                               @AuthenticationPrincipal UserDetails userDetails,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "calc";
        }

        calculatorService.addTodayMeal(userDetails.getUsername(), mealDto.getProductName(), mealDto.getWeight());

        return "redirect:/calories";
    }

}