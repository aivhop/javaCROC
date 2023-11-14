package homework.chernetsov.task11;

import homework.chernetsov.task11.kitchenservice.Cook;
import homework.chernetsov.task11.kitchenservice.DishKitchen;
import homework.chernetsov.task11.kitchenservice.Menu;

import java.util.*;
import java.util.function.Predicate;

public class RoyalKitchen {
    private HashMap<Cook, Set<DishKitchen>> cookDishes;

    public RoyalKitchen(Collection<Cook> cookers) {
        if (cookers == null || cookers.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one cook");
        }
        if (cookers.contains(null)) {
            throw new IllegalArgumentException("Sorry, incorrect cook in collection - null");
        }
        cookDishes = new HashMap<>();
        for (Cook cook : cookers) {
            cookDishes.put(cook, cook.getDishes());
        }
    }

    //todo null or not working telegram check
    public void dismiss(Cook cook) {
        cookDishes.remove(cook);
    }

    public void employ(Cook cook) {
        if (cookDishes.putIfAbsent(cook, cook.getDishes()) != null) {
            throw new IllegalArgumentException("Sorry, such a cook has already been hired");
        }
    }

    public HashMap<Cook, Set<DishKitchen>> getCookDishes() {
        return new HashMap<>(cookDishes);
    }


    //todo see telegram
    public void learnNewRecipe(Collection<DishKitchen> dishes, Collection<Cook> cookers) {
        if (cookers == null || cookers.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there is no one to learn recipes for new dishes");
        }
        //cookers.forEach(Objects::requireNonNull);//?
        for (Cook cook : cookers) {
            if (cook != null) { //or that
                cook.learnNewRecipe(dishes);
                cookDishes.put(cook, cook.getDishes());
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
