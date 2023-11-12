package homework.chernetsov.task11;

import homework.chernetsov.task11.test.TestCook;

import java.util.*;

public class ShowTask {
    public static void main(String[] args) {
        Set<Dish> dishes1 = TestCook.getDishesTest();

        Dish d6 = new Dish("Жареная картошка", Arrays.asList("картошка", "масло", "соль"), Dish.Category.MAIN, 50, 80);
        Dish d7 = new Dish("Вареная картошка", Arrays.asList("картошка", "вода", "соль"), Dish.Category.MAIN, 55, 80);
        Dish d8 = new Dish("Картошка в мундире", Arrays.asList("картошка", "вода", "соль"), Dish.Category.MAIN, 55, 75);


        Cook cook1 = new Cook("Povar First", new HashSet<>(List.of(d6)));
        Cook cook2 = new Cook("Asks", new HashSet<>(List.of(d7)));
        Cook cook3 = new Cook("Povar Second", new HashSet<>(List.of(d8)));
        Cook cook4 = new Cook("What", new HashSet<>(List.of(d6, d7)));
        Cook cook5 = new Cook("Is", new HashSet<>(List.of(d6, d8)));
        Cook cook6 = new Cook("Your", new HashSet<>(List.of(d7, d8)));
        Cook cook7 = new Cook("Profession", dishes1);
        PalaceKitchen kitchen = new PalaceKitchen(Arrays.asList(cook1,cook2,cook3));
        System.out.println("Start:");
        System.out.println(kitchen);



        kitchen.dismiss(cook1);
        System.out.println("Dismiss one: ");
        System.out.println(kitchen);
        System.out.println("Menu first:");
        kitchen.getMenu(kitchen.getCookDishes().keySet(), null, 3,(Dish d) -> d.getLabel().contains("артошка")).forEach(System.out::println);

        System.out.println();

        PalaceKitchen kitchen2 = new PalaceKitchen(Arrays.asList(cook4,cook5,cook6,cook7));
        System.out.println("Menu second:");
        kitchen2.getMenu(List.of(cook7),null,10).forEach(System.out::println);
        System.out.println();
        System.out.println("Menu with absent ingredient:");
        kitchen2.getMenu(List.of(cook7),List.of("tomato"),10).forEach(System.out::println);

        System.out.println("----------------SHOW UPDATE-------------------------");
        kitchen.dismiss(cook1);
        kitchen2.dismiss(cook7);
        kitchen2.dismiss(cook5);
        System.out.println("Kitchen first:");
        System.out.println(kitchen);
        System.out.println("Kitchen second:");
        System.out.println(kitchen2);
        System.out.println("----------Update1-----------");
        kitchen2.updateKitchen(null,kitchen.getCookDishes().keySet());
        System.out.println(kitchen2);
        System.out.println("--------------Update2---------------------------");
        kitchen2.updateKitchen(dishes1, kitchen.getCookDishes().keySet());
        System.out.println(kitchen2);

    }
}
