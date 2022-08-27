package day4.homework;

public class Fibonacci_two {
    public static int fibNum(int i) {
        if (i == 1 || i == 2) {
            return 1;
        } else {
            int t = fibNum(i - 1)+fibNum(i-2);
            return t;
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            int u = fibNum(i);
            System.out.print("\t" + u);
        }
    }
}

