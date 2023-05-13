package com.kizerov.caloriescalculator.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CaloriesSummaryDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private int totalCalories;
    private List<Meal> meals;

}
