package homework.chernetsov.task11;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Dish d = new Dish("123", new ArrayList<String>(Arrays.asList("BREAD")), Dish.Category.MAIN, 10, 4);
        System.out.println(d);
        Integer a = 6;
        a.compareTo(1);


    }
}
