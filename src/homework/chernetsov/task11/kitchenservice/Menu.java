package homework.chernetsov.task11.kitchenservice;

import java.util.*;
import java.util.function.Predicate;

public class Menu {
    public static ArrayList<DishKitchen> getMenu(Collection<Cook> cooksInKitchenToday, Map<Cook, Set<DishKitchen>> allCookAndDish,
                                                 Collection<String> absentIngredients, int limit) {
        return getMenu(cooksInKitchenToday, allCookAndDish, absentIngredients, limit, null);
    }

    public static ArrayList<DishKitchen> getMenu(Collection<Cook> cooksInKitchenToday, Map<Cook, Set<DishKitchen>> allCookAndDish,
                                          Collection<String> absentIngredients, int limit, Predicate<DishKitchen> predicate) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Sorry, the menu can't be empty at the royal feast");
        }
        HashSet<DishKitchen> menu = getAllMenu(cooksInKitchenToday, allCookAndDish, absentIngredients);
        menu = updateByPredicate(menu, predicate);
        ArrayList<DishKitchen> sortedMenu = new ArrayList<>(menu);
        sortedMenu.sort(Collections.reverseOrder());
        return new ArrayList<>(sortedMenu.subList(0, Math.min(limit, menu.size())));
    }

    private static HashSet<DishKitchen> updateByPredicate(HashSet<DishKitchen> menu, Predicate<DishKitchen> predicate) {
        if (predicate != null) {
            HashSet<DishKitchen> newMenu = new HashSet<>(menu.size());
            Iterator<DishKitchen> it = menu.iterator();
            while (it.hasNext()) {
                DishKitchen d = it.next();
                if (predicate.test(d)) {
                    newMenu.add(d);
                }
            }
            return newMenu;
        }
        return menu;
    }


    public static HashSet<DishKitchen> getAllMenu(Collection<Cook> cooksInKitchenToday, Map<Cook, Set<DishKitchen>> allCookAndDish,
                                           Collection<String> absentIngredients) {
        Objects.requireNonNull(cooksInKitchenToday);
        Objects.requireNonNull(allCookAndDish);
        HashSet<DishKitchen> menu = new HashSet<>();
        for (Cook cook : cooksInKitchenToday) {
            Set<DishKitchen> dishes = allCookAndDish.get(cook);
            if (dishes == null) {
                throw new RuntimeException();//todo
            }
            for (DishKitchen dish : dishes) {
                if (canCookDish(dish, absentIngredients)) {
                    menu.add(dish);
                }
            }
        }
        return menu;
    }


    private static boolean canCookDish(DishKitchen dish, Collection<String> absentIngredients) {
        if (absentIngredients != null && !absentIngredients.isEmpty()) {
            for (String ingredient : dish.getIngredients()) {
                for (String absentIngredient : absentIngredients) {
                    if (ingredient.equalsIgnoreCase(absentIngredient)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
