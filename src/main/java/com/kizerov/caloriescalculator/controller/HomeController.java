package com.kizerov.caloriescalculator.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String showHomePage(Model model, Authentication authentication) {

        model.addAttribute("authenticated", authentication != null && authentication.isAuthenticated());

        return "index";
    }

}