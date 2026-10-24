package com.example.cafe.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.cafe.config.AuthInterceptor;
import com.example.cafe.dto.DishForm;
import com.example.cafe.model.Category;
import com.example.cafe.model.Dish;
import com.example.cafe.service.DishService;

import java.util.List;

@Controller
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @ModelAttribute("categories")
    public Category[] categories() {
        return Category.values();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dishes";
    }

    @GetMapping("/dishes")
    public String list(@SessionAttribute(AuthInterceptor.USER_ID) Long userId,
                       @RequestParam(required = false) Category category, Model model) {
        List<Dish> dishes = dishService.findAll(userId, category);
        model.addAttribute("dishes", dishes);
        model.addAttribute("selected", category);
        return "dishes";
    }

    @GetMapping("/dishes/new")
    public String addPage(Model model) {
        model.addAttribute("form", new DishForm());
        return "dish-add";
    }

    @PostMapping("/dishes")
    public String add(@SessionAttribute(AuthInterceptor.USER_ID) Long userId,
                      @Valid @ModelAttribute("form") DishForm form, BindingResult result,
                      RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "dish-add";
        }
        Dish dish = dishService.create(userId, form);
        redirect.addFlashAttribute("message", "Блюдо «" + dish.getName() + "» добавлено");
        return "redirect:/dishes";
    }

    @GetMapping("/dishes/{id}/edit")
    public String editPage(@SessionAttribute(AuthInterceptor.USER_ID) Long userId,
                           @PathVariable Long id, Model model) {
        model.addAttribute("dishId", id);
        model.addAttribute("form", DishForm.from(dishService.findOwn(userId, id)));
        return "dish-edit";
    }

    @PostMapping("/dishes/{id}")
    public String edit(@SessionAttribute(AuthInterceptor.USER_ID) Long userId,
                       @PathVariable Long id,
                       @Valid @ModelAttribute("form") DishForm form, BindingResult result,
                       Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("dishId", id);
            return "dish-edit";
        }
        Dish dish = dishService.update(userId, id, form);
        redirect.addFlashAttribute("message", "Изменения в блюде «" + dish.getName() + "» сохранены");
        return "redirect:/dishes";
    }

    @PostMapping("/dishes/{id}/delete")
    public String delete(@SessionAttribute(AuthInterceptor.USER_ID) Long userId,
                         @PathVariable Long id, RedirectAttributes redirect) {
        String name = dishService.delete(userId, id);
        redirect.addFlashAttribute("message", "Блюдо «" + name + "» удалено");
        return "redirect:/dishes";
    }
}
