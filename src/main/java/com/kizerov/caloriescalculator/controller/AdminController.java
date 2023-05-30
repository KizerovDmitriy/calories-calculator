package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.Food;
import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.repository.FoodsRepository;
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
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final FoodsService foodsService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CalculatorService calculatorService;
    private final FoodsRepository foodsRepository;
    private final int pageSize = 20;

    @GetMapping()
    public String adminCalculator(Model model, @RequestParam(defaultValue = "0") int page, Authentication authentication) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("email").ascending());
        Page<User> userPage = userRepository.findAll(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "admin";
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

    @GetMapping("/products")
    public String productsPage(Model model, @RequestParam(defaultValue = "0") int page, Authentication authentication) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("productName").ascending());
        Page<Food> foodPage = foodsRepository.findAll(pageable);

        model.addAttribute("products", foodPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());

        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "products";
    }

    @GetMapping("/edit/{productId}")
    public String editPage(@PathVariable("productId") Long id, Model model, Authentication authentication) {

        model.addAttribute("product", foodsService.findFoodById(id));
        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "edit";
    }

    @PostMapping("/update/{productId}")
    public String update(@PathVariable("productId") Long id, @ModelAttribute("product") Food food) {

        food.setId(id);
        foodsService.updateFoods(food);

        return "redirect:/admin/products";
    }

    @DeleteMapping("/product/{productName}")
    public String deleteProduct(@PathVariable("productName") String productName) {

        foodsService.deleteFoods(productName);

        return "redirect:/admin/products";
    }

}