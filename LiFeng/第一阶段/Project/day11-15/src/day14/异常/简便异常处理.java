package day14.异常;

public class 简便异常处理 {
    public static void main(String[] args) {
        try {
            int  a  = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("test");
        }
    }
}
