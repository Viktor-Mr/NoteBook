package day06;

public class F_Array_two {
    public static void main(String[] args ) {
        int sum = 0;
        int[] scores = {58,69,88,79,90,82,77,60,95,59,66};
        int max = scores[0];
        int min = scores[0];
        for (int i = 0; i < scores.length; i++) {
            if (min > scores[i])
                min = scores[i];
            if (max < scores[i])
                max = scores[i];
            sum += scores[i];
        }
        System.out.println("Sum = " +sum + ", Aver = " +sum*1.0/scores.length + ", Max = " + max + ", Min = " + min +".");

    }
}
