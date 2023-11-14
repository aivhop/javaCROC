package homework.chernetsov.task11.test;

import homework.chernetsov.task11.kitchenservice.DishKitchen;
import homework.chernetsov.task11.exception.IncorrectRatingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class TestDishKitchen {
    public static ArrayList<String> getIngredientsTest() {
        return new ArrayList<>(Arrays.asList("tomato", "water", "salt", "carrot", "bread"));
    }

    public DishKitchen.Category getNormalCategory() {
        return DishKitchen.Category.MAIN;
    }

    public DishKitchen.Category getNullCategory() {
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

    public DishKitchen getCorrectDish() {
        return new DishKitchen(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating());
    }

    @Test
    public void testDishLabelNull() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new DishKitchen(null, getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishLabelEmpty() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new DishKitchen(getEmptyLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishNormal() {
        assertEquals(new DishKitchen(getCorrectLabel(), getIngredientsTest(),
                getNormalCategory(), getCorrectRating(), getCorrectRating()),
                getCorrectDish());

    }
    @Test
    public void testDishRatingKingIncorrect(){
        Throwable thrown = assertThrows(IncorrectRatingException.class, () ->
                new DishKitchen(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getIncorrectRating(), getCorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testDishRatingCourtiersIncorrect(){
        Throwable thrown = assertThrows(IncorrectRatingException.class, () ->
                new DishKitchen(getCorrectLabel(), getIngredientsTest(), getNormalCategory(), getCorrectRating(), getIncorrectRating())
        );
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testDishCompareEqualsHash(){
        DishKitchen d1 = new DishKitchen(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,40);
        DishKitchen d2 = new DishKitchen(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,39);
        DishKitchen d3 = new DishKitchen(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),40,50);
        assertEquals(d1.compareTo(d2) > 0, true);
        assertEquals(d1.compareTo(d3) > 0, true);
        DishKitchen d4 = new DishKitchen(getCorrectLabel(),getIngredientsTest(),getNormalCategory(),50,40);
        assertEquals(d1.compareTo(d4), 0);
        assertEquals(d1.equals(d4), true);
        assertEquals(d1.hashCode() == d4.hashCode(), true);
    }
}
