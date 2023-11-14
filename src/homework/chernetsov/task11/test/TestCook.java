package homework.chernetsov.task11.test;

import homework.chernetsov.task11.kitchenservice.Cook;
import homework.chernetsov.task11.kitchenservice.DishKitchen;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCook {
    public static Set<DishKitchen> getDishesTest() {
        ArrayList<String> another = new ArrayList<>();
        another.add("cucumber");
        DishKitchen d1 = new DishKitchen("label1", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.BEVERAGE, 30, 20);
        DishKitchen d2 = new DishKitchen("label2", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.MAIN, 30, 40);
        DishKitchen d3 = new DishKitchen("label3", another, DishKitchen.Category.SALAD, 30, 10);
        DishKitchen d4 = new DishKitchen("label4", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.SOUP, 40, 20);
        DishKitchen d5 = new DishKitchen("label5", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.SAUCE, 50, 20);
        return new HashSet<>(Arrays.asList(d1, d2, d3, d4, d5));
    }
    public static Set<DishKitchen> getDishesTest2() {
        DishKitchen d1 = new DishKitchen("label1", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.DESSERT, 70, 20);
        DishKitchen d2 = new DishKitchen("label2", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.SOUP, 20, 40);
        DishKitchen d3 = new DishKitchen("label3", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.BEVERAGE, 30, 10);
        DishKitchen d4 = new DishKitchen("label4", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.MAIN, 100, 20);
        DishKitchen d5 = new DishKitchen("label5", TestDishKitchen.getIngredientsTest(), DishKitchen.Category.SAUCE, 0, 20);
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
