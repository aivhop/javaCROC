package task12;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ShowTask {
    public static void main(String[] args) {
        int a = 1;
        int b = 5;
        int x = a - b;

        System.out.println("Standard ternary: " + (x > 0 ? x + 100 : -x + 100));
        Predicate<Integer> predicate = value -> value > 0;
        Function<Integer, Integer> foo1 = value -> value + 100;
        Function<Integer, Integer> foo2 = value -> -value + 100;
        System.out.println("My ternary: " + (MyTernaryOperator.ternary(x, predicate, foo1, foo2)));


        String str = "english";

        Predicate<String> isEnglishWord = value -> Pattern.matches("[A-Za-z]+", value);
        Function<String, String> withEngWord = value -> "It is English word";
        Function<String, String> withNoEngWord = value -> "It is not English word";

        System.out.println("Standard ternary: " + (Pattern.matches("[A-Za-z]+", str) ? "It is English word" : "It is not English word"));
        System.out.println("My ternary: " + (MyTernaryOperator.ternary(str, isEnglishWord, withEngWord, withNoEngWord)));

        x = 100;

        Predicate<Integer> predicate1 = value -> value > 0;
        Function<Integer, Integer> funct1 = value -> value;
        Function<Integer, String> funct2 = value -> "Not positive" + value;

        System.out.println("Standard ternary: " + (x > 0 ? x : "Not positive" + x));
        System.out.println("My ternary: " + (MyTernaryOperator.ternary(x, predicate1, funct1, funct2)));

        x = -100;

        System.out.println("Standard ternary: " + (x > 0 ? x : "Not positive" + x));
        System.out.println("My ternary: " + (MyTernaryOperator.ternary(x, predicate1, funct1, funct2)));


        Function<Integer, Integer> funct3 = value -> a;
        Function<Integer, Integer> funct4 = value -> b;
        System.out.println("Standard ternary: " + (x > 0 ? a : b));
        System.out.println("My ternary: " + (MyTernaryOperator.ternary(x, predicate1, funct3, funct4)));
    }
}
