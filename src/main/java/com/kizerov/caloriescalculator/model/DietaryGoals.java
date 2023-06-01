package com.kizerov.caloriescalculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DietaryGoals {

    private int calories;
    private int proteins;
    private int fats;
    private int carbohydrates;

}
