package homework.chernetsov.task11;

import homework.chernetsov.task11.kitchenservice.Cook;
import homework.chernetsov.task11.kitchenservice.DishKitchen;
import homework.chernetsov.task11.kitchenservice.Menu;

import java.util.*;
import java.util.function.Predicate;

public class RoyalKitchen {
    private final HashMap<Cook, Set<DishKitchen>> cookDishes;

    public RoyalKitchen(Collection<Cook> cookers) {
        if (cookers == null || cookers.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one cook");
        }
        cookers.forEach(Objects::requireNonNull);
        cookDishes = new HashMap<>();
        for (Cook cook : cookers) {
            cookDishes.put(cook, cook.getDishes());
        }
    }
    public boolean dismiss(Cook cook) {
        return cookDishes.remove(cook) != null;
    }

    public boolean employ(Cook cook) {
        return cookDishes.putIfAbsent(cook, cook.getDishes()) == null;
    }

    public HashMap<Cook, Set<DishKitchen>> getCookDishes() {
        return new HashMap<>(cookDishes);
    }


    public void learnNewRecipe(Collection<DishKitchen> dishes, Collection<Cook> cookers) {
        if (cookers == null || cookers.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there is no one to learn recipes for new dishes");
        }
        for (Cook cook : cookers) {
            if (cook != null) {
                cook.learnNewRecipe(dishes);
                cookDishes.put(cook, cook.getDishes());
            }else{
                throw new IllegalArgumentException("Sorry, cook can't be null");
            }
        }
    }

    public void learnNewRecipe(DishKitchen dish, Collection<Cook> cookers) {
        ArrayList<DishKitchen> temp = new ArrayList<>();
        temp.add(dish);
        learnNewRecipe(temp, cookers);
    }


    public ArrayList<DishKitchen> getMenu(Collection<Cook> cookers, Collection<String> absentIngredients, int limit) {
        return Menu.getMenu(cookers, this.cookDishes, absentIngredients, limit);
    }

    public ArrayList<DishKitchen> getMenu(Collection<Cook> cookers, Collection<String> absentIngredients, int limit, Predicate<DishKitchen> predicate) {
        return Menu.getMenu(cookers, this.cookDishes, absentIngredients, limit, predicate);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Cook cook : cookDishes.keySet()) {
            out.append(cook.toString())
                    .append('\n');
        }
        return out.toString();
    }
}
