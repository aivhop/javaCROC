package homework.first.task2;

public class SecondTask {
    public static void task() {
        for (int i = 1; i <= 100; ++i) {
            if(i % 3 == 0){
                System.out.print("Fizz");
                if (i % 5 == 0) {
                    System.out.print("Buzz");
                }
            }
            else if (i % 5 == 0) {
                System.out.print("Buzz");
            }
            else{
                System.out.print(i);
            }
            System.out.println();
        }
    }

}

