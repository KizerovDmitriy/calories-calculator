package com.kizerov.caloriescalculator.service;

import com.kizerov.caloriescalculator.model.CaloriesSummaryDto;

import java.time.LocalDate;

public interface ICalcService {

    void addTodayMeal(String userEmail, String productName, int weight);

    CaloriesSummaryDto showStatistics(String email, LocalDate startDate, LocalDate endDate);

    CaloriesSummaryDto showStatistics(String email);

}
