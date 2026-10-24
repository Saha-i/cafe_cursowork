package com.example.cafe.model;

public enum Category {
    HOT("Горячее", "г"),
    SALADS("Салаты", "г"),
    DESSERTS("Десерты", "г"),
    DRINKS("Напитки", "мл");

    private final String title;
    private final String unit;

    Category(String title, String unit) {
        this.title = title;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }
}



