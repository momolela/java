package com.momolela.builderpattern;

public class NonVegMealBuilder implements MealBuilder {
    private Meal meal;

    public NonVegMealBuilder() {
        meal = new Meal();
    }

    @Override
    public void buildBurger() {
        meal.addItem(new ChickenBurger());
    }

    @Override
    public void buildDrink() {
        meal.addItem(new Pepsi());
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}