package com.example.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private Category category;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    protected Dish() {
    }

    public Dish(User user, String name, Category category, BigDecimal price, Integer weight) {
        this.user = user;
        update(name, category, price, weight);
    }

    public void update(String name, Category category, BigDecimal price, Integer weight) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getWeight() {
        return weight;
    }
}
