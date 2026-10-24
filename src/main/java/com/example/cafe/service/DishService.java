package com.example.cafe.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.cafe.dto.DishForm;
import com.example.cafe.model.Category;
import com.example.cafe.model.Dish;
import com.example.cafe.model.User;
import com.example.cafe.repository.DishRepository;
import com.example.cafe.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    public DishService(DishRepository dishRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Dish> findAll(Long userId, Category category) {
        if (category == null) {
            return dishRepository.findByUserId(userId).stream()
                    .sorted(Comparator.comparing(Dish::getCategory)
                            .thenComparing(Dish::getName, String.CASE_INSENSITIVE_ORDER))
                    .toList();
        }
        return dishRepository.findByUserIdAndCategoryOrderByNameAsc(userId, category);
    }

    @Transactional(readOnly = true)
    public Dish findOwn(Long userId, Long dishId) {
        return dishRepository.findByIdAndUserId(dishId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Блюдо не найдено"));
    }

    @Transactional
    public Dish create(Long userId, DishForm form) {
        User owner = userRepository.getReferenceById(userId);
        Dish dish = new Dish(owner, form.getName().trim(), form.getCategory(),
                form.getPrice(), form.getWeight());
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish update(Long userId, Long dishId, DishForm form) {
        Dish dish = findOwn(userId, dishId);
        dish.update(form.getName().trim(), form.getCategory(), form.getPrice(), form.getWeight());
        return dish;
    }

    @Transactional
    public String delete(Long userId, Long dishId) {
        Dish dish = findOwn(userId, dishId);
        dishRepository.delete(dish);
        return dish.getName();
    }
}
