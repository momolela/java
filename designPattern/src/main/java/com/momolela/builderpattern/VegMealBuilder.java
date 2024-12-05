package com.momolela.builderpattern;

public class VegMealBuilder implements MealBuilder {
    private Meal meal;

    public VegMealBuilder() {
        meal = new Meal();
    }

    @Override
    public void buildBurger() {
        meal.addItem(new VegBurger());
    }

    @Override
    public void buildDrink() {
        meal.addItem(new Coke());
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}