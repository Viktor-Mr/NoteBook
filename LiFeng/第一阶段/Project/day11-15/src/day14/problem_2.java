package day14;

public class problem_2 {
    static int problem_f(int i) {
        try {
            int a =10/0;
        }catch(Exception e) {

            return i+1;

        }finally {
            i = i+2;
            return i;
        }

    }

    public static void main(String[] args) {
        int a[] = {10,20,23};
        System.out.println(problem_f(1));
    }
}