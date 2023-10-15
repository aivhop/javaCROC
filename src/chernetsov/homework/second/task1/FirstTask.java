package chernetsov.homework.second.task1;

public class FirstTask {
    public static void main(String[] args) {
        long value = Long.parseLong(args[0]);
        System.out.println(whatNumber(value));
    }
    private static Answer whatNumber(long num) {
        if(num >= 2 && num <= 10_000_000_000L) {
            if (isPrime(num)) {
                if (isPrime(num + 2) || isPrime(num - 2)) {
                    return Answer.Twin;
                } else {
                    return Answer.Prime;
                }
            } else {
                return Answer.Composite;
            }
        }
        else{
            return Answer.IncorrectInput;
        }
    }
    private static boolean isPrime(long num) {
        long delimiter = (long) Math.ceil(Math.sqrt(num));
        boolean isPrime = num > 1 && delimiter * delimiter != num;
        long divider = 2;
        while (isPrime && divider < delimiter) {
            isPrime = (num % divider != 0);
            divider++;
        }
        return isPrime;
    }

    private enum Answer {Prime, Twin, Composite, IncorrectInput}

}
