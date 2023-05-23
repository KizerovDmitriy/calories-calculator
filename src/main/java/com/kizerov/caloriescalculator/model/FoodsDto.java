package com.kizerov.caloriescalculator.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FoodsDto {

    @NotNull
    @NotEmpty
    private String productName;
    @NotNull
    @NotEmpty
    private Integer caloriesPer100Gram;
    @NotNull
    @NotEmpty
    private int protein;
    @NotNull
    @NotEmpty
    private int fat;
    @NotNull
    @NotEmpty
    private int carbohydrates;

}