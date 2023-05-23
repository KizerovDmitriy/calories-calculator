package com.kizerov.caloriescalculator.repository;

import com.kizerov.caloriescalculator.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodsRepository extends JpaRepository<Food, Long> {

    Optional<Food> findProductByProductName(String productName);

    void deleteProductByProductName(String name);

    List<Food> findAll();

}