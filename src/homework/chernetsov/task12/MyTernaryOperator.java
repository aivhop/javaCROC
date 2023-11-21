package homework.chernetsov.task12;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyTernaryOperator {

    public static <T> Function<T, ?> ternary(Predicate<T> predicate, Function<T, ?> foo1, Function<T, ?> foo2) {
        return (value) -> predicate.test(value) ? foo1.apply(value) : foo2.apply(value);
    }
}
