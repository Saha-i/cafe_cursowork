package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cafe.model.Category;
import com.example.cafe.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByUserId(Long userId);

    List<Dish> findByUserIdAndCategoryOrderByNameAsc(Long userId, Category category);

    Optional<Dish> findByIdAndUserId(Long id, Long userId);
}


