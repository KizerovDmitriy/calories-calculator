package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.User;
import com.kizerov.caloriescalculator.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account-page")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping()
    public String accountPage(Model model, Authentication authentication) {

        model.addAttribute("userInfo", userService.findUserByEmail(authentication.getName()));
        model.addAttribute("authenticated", authentication.isAuthenticated());

        return "account-page";
    }

    @PostMapping("/update/{userId}")
    public String update(@PathVariable("userId") Long id, @ModelAttribute("userInfo") User user) {

        userService.updateUserFields(id, user.getSex(), user.getAge(), user.getHeight(), user.getWeight());

        return "redirect:/account-page";
    }

}
