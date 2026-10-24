package com.example.cafe.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.example.cafe.model.Category;
import com.example.cafe.model.Dish;

import java.math.BigDecimal;

public class DishForm {
    @NotBlank(message = "Введите название блюда")
    @Size(max = 100, message = "Название не длиннее 100 символов")
    private String name;

    @NotNull(message = "Выберите категорию")
    private Category category;

    @NotNull(message = "Укажите цену")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше нуля")
    @DecimalMax(value = "1000000", message = "Слишком большая цена")
    @Digits(integer = 7, fraction = 2, message = "Не более двух знаков после запятой")
    private BigDecimal price;

    @NotNull(message = "Укажите вес порции")
    @Min(value = 1, message = "Вес должен быть больше нуля")
    @Max(value = 10000, message = "Вес не более 10000")
    private Integer weight;

    public static DishForm from(Dish dish) {
        DishForm form = new DishForm();
        form.setName(dish.getName());
        form.setCategory(dish.getCategory());
        form.setPrice(dish.getPrice());
        form.setWeight(dish.getWeight());
        return form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
