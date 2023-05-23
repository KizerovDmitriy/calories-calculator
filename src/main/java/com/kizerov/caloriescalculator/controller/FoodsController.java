package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.model.FoodsDto;
import com.kizerov.caloriescalculator.service.impl.FoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodsController {

    private final FoodsService foodsService;

    @GetMapping()
    public String showUpdateForm(Model model) {
        model.addAttribute("foodsDto", new FoodsDto());
        return "calc";
    }


    @PostMapping("/add-new-foods")
    public String add(@ModelAttribute("foodsDto") @Valid FoodsDto foodsDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "calc";
        }

        foodsService.addNewFoods(foodsDto);

        return "redirect:/calories";
    }

}