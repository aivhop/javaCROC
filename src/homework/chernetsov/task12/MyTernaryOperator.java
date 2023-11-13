package homework.chernetsov.task12;

import java.util.function.Function;
import java.util.function.Predicate;

public class MyTernaryOperator {
    public static <T, R1, R2> Object ternary(T value, Predicate<T> predicate, Function<T, R1> foo1, Function<T, R2> foo2) {
        if (predicate.test(value)) {
            return foo1.apply(value);
        }
        return foo2.apply(value);
    }
}
