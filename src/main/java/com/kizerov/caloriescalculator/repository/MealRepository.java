package com.kizerov.caloriescalculator.repository;

import com.kizerov.caloriescalculator.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT e FROM Meal e WHERE e.user.email = :email AND e.food.productName = :productName AND e.date = :currentDate")
    Optional<Meal> findByUserEmailAndProductName(@Param("email") String email, @Param("productName") String productName,
                                                 @Param("currentDate") LocalDate currentData);

    @Query("SELECT e from Meal e WHERE e.user.email = :email AND e.date between :startDate and :endDate")
    Optional<List<Meal>> findAllByUserEmailAndDate(@Param("email") String email, @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

}
