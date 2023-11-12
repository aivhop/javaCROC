package homework.chernetsov.task11;

import java.util.*;
import java.util.function.Predicate;

public class PalaceKitchen {
    private HashMap<Cook, Set<Dish>> cookDishes;

    public PalaceKitchen(Collection<Cook> cookers) {
        setCookDishes(cookers);
    }

    public void dismiss(Cook cook) {
        cookDishes.remove(cook);
    }

    public void employ(Cook cook) {
        if (cookDishes.putIfAbsent(cook, cook.getDishes()) != null) {
            throw new IllegalArgumentException("Sorry, such a cook has already been hired");
        }
    }

    public HashMap<Cook, Set<Dish>> getCookDishes() {
        return new HashMap<>(cookDishes);
    }

    public void setCookDishes(Collection<Cook> cookers) {
        if (cookers == null || cookers.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one cook");
        }
        HashMap<Cook, Set<Dish>> newCookDish = new HashMap<>();
        for (Cook cook : cookers) {
            if (cook != null) {
                newCookDish.put(cook, cook.getDishes());
            }
        }
        this.cookDishes = newCookDish;
    }

    public void updateKitchen(Collection<Dish> dishes, Collection<Cook> cookers) {
        for (Cook cook : cookers) {
            if (cook != null) {
                cook.update(dishes);
                cookDishes.put(cook, cook.getDishes());
            }
        }
    }


    public ArrayList<Dish> getMenu(Collection<Cook> cookers, Collection<String> absentIngredients, int limit) {
        return getMenu(cookers, absentIngredients, limit, null);
    }

    public ArrayList<Dish> getMenu(Collection<Cook> cookers, Collection<String> absentIngredients, int limit, Predicate<Dish> predicate) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Sorry, the menu can't be empty at the feast");
        }
        ArrayList<Dish> menu = getAllMenu(cookers, absentIngredients);
        menu = updateByPredicate(menu, predicate);
        menu.sort(Collections.reverseOrder());
        return new ArrayList<>(menu.subList(0, Math.min(limit, menu.size())));
    }

    private ArrayList<Dish> updateByPredicate(ArrayList<Dish> menu, Predicate<Dish> predicate) {
        if (predicate != null) {
            ArrayList<Dish> newMenu = new ArrayList<>(menu.size());
            Iterator<Dish> it = menu.iterator();
            while (it.hasNext()) {
                Dish d = it.next();
                if (predicate.test(d)) {
                    newMenu.add(d);
                }
            }
            return newMenu;
        }
        return menu;
    }

    public ArrayList<Dish> getAllMenu(Collection<Cook> cookers, Collection<String> absentIngredients) {
        Objects.requireNonNull(cookers);
        HashSet<Dish> menu = new HashSet<>();
        for (Cook cook : cookers) {
            Set<Dish> dishes = cookDishes.get(cook);
            for (Dish dish : dishes) {
                if (canCookDish(dish, absentIngredients)) {
                    menu.add(dish);
                }
            }
        }
        return new ArrayList<>(menu);
    }

    private boolean canCookDish(Dish dish, Collection<String> absentIngredients) {
        if (absentIngredients != null && !absentIngredients.isEmpty()) {
            Set<String> ingredients = dish.getIngredients();
            for (String ingredient : ingredients) {
                for (String absentIngredient : absentIngredients) {
                    if (ingredient.equalsIgnoreCase(absentIngredient)) {
                        return false;
                    }
                }
            }
        }
        return true;
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
