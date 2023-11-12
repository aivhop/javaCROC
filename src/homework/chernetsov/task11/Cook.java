package homework.chernetsov.task11;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class Cook {
    private String fullName;

    private Set<Dish> dishes;

    public Cook(String fullName, Set<Dish> dishes) {
        setFullName(fullName);
        setDishes(dishes);
    }

    @Override
    public String toString() {
        return fullName + ", dishes=" + dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cook cook = (Cook) o;
        return Objects.equals(fullName, cook.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    public void update(Collection<Dish> dishes) {
        if (dishes != null) {
            for (Dish dish : dishes) {
                if (dish != null) {
                    this.dishes.add(dish);
                }
            }
        }
    }

    public void setFullName(String fullName) {
        if (fullName == null || !isCorrectName(fullName)) {
            throw new IllegalArgumentException("Sorry, incorrect full name entered");
        }
        this.fullName = fullName;
    }

    public void setDishes(Set<Dish> dishes) {
        if (dishes == null || dishes.isEmpty()) {
            throw new IllegalArgumentException("Sorry, there must be at least one dish");
        }
        this.dishes = dishes;
        dishes.remove(null);
    }

    public String getFullName() {
        return fullName;
    }

    public HashSet<Dish> getDishes() {
        return new HashSet<>(dishes);
    }

    private boolean isCorrectName(String name) {
        return Pattern.matches("[A-Z][a-z]*( [A-Z][a-z]*)?( [A-Z][a-z]*)?", name);
    }
}
