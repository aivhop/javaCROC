package homework.chernetsov.task11.test;

import homework.chernetsov.task11.Dish;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class TestDish {
    public static ArrayList<String> getIngredientsTest() {
        return new ArrayList<>(Arrays.asList("tomato", "water", "salt", "carrot", "bread"));
    }

    public Dish.Category getNormalCategory() {
        return Dish.Category.MAIN;
    }

    public Dish.Category getNullCategory() {
        return null;
    }

    public int getCorrectRating() {
        return 50;
    }

    public int getIncorrectRating() {
        return -1;
    }

    public String getCorrectLabel() {
        return "test";
    }

    public String getNullLabel() {
        return null;
    }

    public String getEmptyLabel() {
        return "";
    }

    public Dish getCorrectDish() {
        return new Dish(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating());
    }

    @Test
    public void testDishLabelNull() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Dish(null, getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishLabelEmpty() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Dish(getEmptyLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishNormal() {
        assertEquals(new Dish(getCorrectLabel(), getIngredientsTest(),
                getNormalCategory(), getCorrectRating(), getCorrectRating()),
                getCorrectDish());

    }
    @Test
    public void testDishRatingKingIncorrect(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Dish(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getIncorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testDishRatingCourtiersIncorrect(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Dish(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getIncorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishCompareEqualsHash(){
        Dish d1 = new Dish(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,40);
        Dish d2 = new Dish(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,39);
        Dish d3 = new Dish(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),40,50);
        assertEquals(d1.compareTo(d2) > 0, true);
        assertEquals(d1.compareTo(d3) > 0, true);
        Dish d4 = new Dish(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,40);
        assertEquals(d1.compareTo(d4), 0);
        assertEquals(d1.equals(d4), true);
        assertEquals(d1.hashCode() == d4.hashCode(), true);
    }
}
