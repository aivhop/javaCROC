package homework.chernetsov.task4;

public class Task4Main {
    public static void main(String[] args) {
        int start = Integer.parseInt(args[0]);
        int difference = Integer.parseInt(args[1]);
        int quantity = Integer.parseInt(args[2]);
        System.out.println(arithmeticProgression(start,difference,quantity));
    }

    private static long arithmeticProgression(int start, int difference, int quantity) {
        long sum = 0;
        long value = start;
        while (quantity > 0) {
            sum += value;
            value += difference;
            quantity--;
        }
        return sum;
    }
}
