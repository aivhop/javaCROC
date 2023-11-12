package homework.chernetsov.task11.test;

import homework.chernetsov.task11.Cook;
import homework.chernetsov.task11.Dish;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCook {
    public static Set<Dish> getDishesTest() {
        Dish d1 = new Dish("label1", TestDish.getIngredientsTest(), Dish.Category.BEVERAGE, 30, 20);
        Dish d2 = new Dish("label2", TestDish.getIngredientsTest(), Dish.Category.MAIN, 30, 40);
        Dish d3 = new Dish("label3", List.of("cucumber"), Dish.Category.SALAD, 30, 10);
        Dish d4 = new Dish("label4", TestDish.getIngredientsTest(), Dish.Category.SOUP, 40, 20);
        Dish d5 = new Dish("label5", TestDish.getIngredientsTest(), Dish.Category.SAUCE, 50, 20);
        return new HashSet<>(Arrays.asList(d1, d2, d3, d4, d5));
    }
    public static Set<Dish> getDishesTest2() {
        Dish d1 = new Dish("label1", TestDish.getIngredientsTest(), Dish.Category.DESSERT, 70, 20);
        Dish d2 = new Dish("label2", TestDish.getIngredientsTest(), Dish.Category.SOUP, 20, 40);
        Dish d3 = new Dish("label3", TestDish.getIngredientsTest(), Dish.Category.BEVERAGE, 30, 10);
        Dish d4 = new Dish("label4", TestDish.getIngredientsTest(), Dish.Category.MAIN, 100, 20);
        Dish d5 = new Dish("label5", TestDish.getIngredientsTest(), Dish.Category.SAUCE, 0, 20);
        return new HashSet<>(Arrays.asList(d1, d2, d3, d4, d5));
    }

    @Test
    public void testName1() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Cook("123",getDishesTest() )
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testName2() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Cook("Al1",getDishesTest() )
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testName3() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Cook("Al d",getDishesTest() )
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testName4() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
                new Cook("Al A d",getDishesTest() )
        );
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void testName5() {
        Cook cook = new Cook("Al A A",getDishesTest());
        assertEquals(cook.getFullName(),"Al A A");
    }

}
