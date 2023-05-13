package com.kizerov.caloriescalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MealDto {

    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String productName;
    @NotNull
    @NotEmpty
    private int weight;

}
