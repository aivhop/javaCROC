package homework.chernetsov.task11.kitchenservice;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class Cook {
    private static int nextId = 0;

    private final int id;
    private String fullName;

    private Set<DishKitchen> dishes;

    public Cook(String fullName, Set<DishKitchen> dishes) {
        setFullName(fullName);
        setDishes(dishes);
        id = nextId;
        nextId++;
    }

    @Override
    public String toString() {
        return fullName + ", dishes=" + dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return id == ((Cook) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void learnNewRecipe(Collection<DishKitchen> dishes) {
        if (dishes != null && !dishes.contains(null)) {
            this.dishes.addAll(dishes);
        } else {
            throw new IllegalArgumentException("Sorry, the dishes can't contain null");
        }
    }

    public void learnNewRecipe(DishKitchen dish) {
        if (dish != null){
            this.dishes.add(dish);
        }
    }

    public void setFullName(String fullName) {
        if (fullName == null || !isCorrectName(fullName)) {
            throw new IllegalArgumentException("Sorry, incorrect full name entered");
        }
        this.fullName = fullName;
    }

    public void setDishes(Set<DishKitchen> dishes) {
        if (dishes == null || dishes.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one dish");
        } else if (dishes.contains(null)) {
            throw new IllegalArgumentException("Sorry, the dishes can't contain null");
        }
        this.dishes = dishes;

    }

    public String getFullName() {
        return fullName;
    }

    public HashSet<DishKitchen> getDishes() {
        return new HashSet<>(dishes);
    }

    public int getId() {
        return id;
    }

    private boolean isCorrectName(String name) {
        return Pattern.matches("[A-Z][a-z]*( [A-Z][a-z]*)?( [A-Z][a-z]*)?", name);
    }
}
