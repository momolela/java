package com.momolela.builderpattern;

public interface MealBuilder {
    void buildBurger();
    void buildDrink();
    Meal getMeal();
}