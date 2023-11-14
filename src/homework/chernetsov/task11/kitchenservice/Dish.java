package homework.chernetsov.task11.kitchenservice;

import java.util.*;

public class Dish {
    public enum Category {
        MAIN,
        SNACK,
        DESSERT,
        BEVERAGE,
        SAUCE,
        SOUP,
        SALAD
    }

    private final String label;
    private final List<String> ingredients;
    private final Category category;

    public Dish(String label, List<String> ingredients, Category category) {
        if (label == null || label.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the label can't be empty");
        }
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one ingredient");
        } else if (ingredients.contains(null)) {
            throw new IllegalArgumentException("Sorry, incorrect ingredient in collection - null");
        }
        this.label = label;
        this.ingredients = ingredients;
        this.category = category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, ingredients, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(label, dish.label) && Objects.equals(ingredients, dish.ingredients) && category == dish.category;
    }

    @Override
    public String toString() {
        return label + ": " + ingredients +
                ", category: " + category;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public Category getCategory() {
        return category;
    }
}
