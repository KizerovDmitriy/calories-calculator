package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.FoodsDto;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.repository.UserRepository;
import com.kizerov.caloriescalculator.service.impl.CalculatorService;
import com.kizerov.caloriescalculator.service.impl.FoodsService;
import com.kizerov.caloriescalculator.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final FoodsService foodsService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CalculatorService calculatorService;

    @GetMapping()
    public String adminCalculator(Model model, @RequestParam(defaultValue = "0") int page, Authentication authentication) {

        int pageSize = 20;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("email").ascending());
        Page<User> userPage = userRepository.findAll(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "admin";
    }


    @PutMapping()
    public String update(@RequestParam("productName") String productName,
                         @ModelAttribute("foodsDto") @Valid FoodsDto foodsDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "redirect:/error";

        foodsService.updateFoods(productName, foodsDto);

        return "redirect:/calories";
    }

    @DeleteMapping()
    public String deleteProduct(@RequestParam("productName") String productName) {

        foodsService.deleteFoods(productName);

        return "redirect:/calories";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String adminStatus(@PathVariable("id") Long id) {

        userService.adminStatus(id);

        return "redirect:/admin";
    }

    @PostMapping("/{email}")
    public String showStatistics(@PathVariable("email") String email, Model model, Authentication authentication) {

        model.addAttribute("statistics", calculatorService.showStatistics(email));
        model.addAttribute("email", email);
        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "admin-panel-statistics";
    }

}