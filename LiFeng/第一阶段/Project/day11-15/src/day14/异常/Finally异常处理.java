package day14.异常;

public class Finally异常处理 {
    public static void main(String[] args) {
        int num=0;
        try {
            num = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("无法在此输出num");
        }
        System.out.println(num);
    }
}
