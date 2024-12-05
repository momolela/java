package com.momolela.builderpattern;

public class App {
    public static void main(String[] args) {
        MealBuilder vegMealBuilder = new VegMealBuilder();
        vegMealBuilder.buildBurger();
        vegMealBuilder.buildDrink();
        Meal vegMeal = vegMealBuilder.getMeal();
        vegMeal.showItems();
        System.out.println("Total Cost: " + vegMeal.getCost());

        MealBuilder nonVegMealBuilder = new NonVegMealBuilder();
        nonVegMealBuilder.buildBurger();
        nonVegMealBuilder.buildDrink();
        Meal nonVegMeal = nonVegMealBuilder.getMeal();
        nonVegMeal.showItems();
        System.out.println("Total Cost: " + nonVegMeal.getCost());
    }
}