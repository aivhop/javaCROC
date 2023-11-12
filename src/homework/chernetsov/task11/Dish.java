package homework.chernetsov.task11;

import java.util.*;

public class Dish implements Comparable<Dish> {
    public enum Category {
        MAIN,
        SNACK,
        DESSERT,
        BEVERAGE,
        SAUCE,
        SOUP,
        SALAD
    }

    public static final int DEFAULT_COOKING_TIME = 15;

    private String label;
    private Collection<String> ingredients;
    private final Category category;
    private int ratingKing;
    private int ratingCourtiers;
    private int cookingTimeInMinutes;

    public Dish(String label, Collection<String> ingredients, Category category,
                int ratingKing, int ratingCourtiers, int cookingTimeInMinutes) {
        setLabel(label);
        setIngredients(ingredients);
        this.category = category;
        setRatingKing(ratingKing);
        setRatingCourtiers(ratingCourtiers);
        setCookingTimeInMinutes(cookingTimeInMinutes);
    }

    public Dish(String label, Collection<String> ingredients, Category category, int ratingKing, int ratingCourtiers) {
        this(label, ingredients, category, ratingKing, ratingCourtiers, DEFAULT_COOKING_TIME);
    }

    @Override
    public String toString() {
        return label + ingredients + ' ' + category + " dish, rating King: " + ratingKing +
                ", rating Courtiers: " + ratingCourtiers +
                ", cooking time: " + cookingTimeInMinutes + 'm';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return ratingKing == dish.ratingKing &&
                ratingCourtiers == dish.ratingCourtiers &&
                cookingTimeInMinutes == dish.cookingTimeInMinutes &&
                Objects.equals(label, dish.label) &&
                Objects.equals(ingredients, dish.ingredients) &&
                category == dish.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, ingredients, category, ratingKing, ratingCourtiers, cookingTimeInMinutes);
    }

    @Override
    public int compareTo(Dish o) {
        if (ratingKing == o.ratingKing) {
            if (ratingCourtiers == o.ratingCourtiers) {
                return 0;
            }
            return ratingCourtiers > o.ratingCourtiers ? 1 : -1;
        }
        return ratingKing > o.ratingKing ? 1 : -1;
    }

    public void setLabel(String label) {
        if (label == null || label.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the label can't be empty");
        }
        this.label = label;
    }

    public void setIngredients(Collection<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one ingredient");
        }
        this.ingredients = ingredients;
    }

    public void setRatingKing(int ratingKing) {
        if (!isCorrectRating(ratingKing)) {
            throw new IllegalArgumentException("Sorry, the score from the king should be from 0 to 100");
        }
        this.ratingKing = ratingKing;
    }

    public void setRatingCourtiers(int ratingCourtiers) {
        if (!isCorrectRating(ratingCourtiers)) {
            throw new IllegalArgumentException("Sorry, the score from the courtiers should be from 0 to 100");
        }
        this.ratingCourtiers = ratingCourtiers;
    }

    public void setCookingTimeInMinutes(int cookingTimeInMinutes) {
        if (cookingTimeInMinutes <= 0) {
            throw new IllegalArgumentException("Sorry, the dish won't cook instantly");
        }
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public String getLabel() {
        return label;
    }

    public Set<String> getIngredients() {
        return new HashSet<>(ingredients);
    }

    public Category getCategory() {
        return category;
    }

    public int getRatingKing() {
        return ratingKing;
    }

    public int getRatingCourtiers() {
        return ratingCourtiers;
    }

    public int getCookingTimeInMinutes() {
        return cookingTimeInMinutes;
    }

    private boolean isCorrectRating(int rating) {
        return rating <= 100 && rating >= 0;
    }
}
