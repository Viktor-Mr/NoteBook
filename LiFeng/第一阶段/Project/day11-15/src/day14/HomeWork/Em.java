package day14.HomeWork;

public class Em {
    static String regex = "\\w{3,}@\\w+\\.\\w{2,4}";
    public static void main(String[] args) throws  Exception{

        String em = "1915";
        try {
            test(em);
        } catch (Exception e) {
            System.out.println(e.getMessage( ));
        }
        System.out.println("测试成功");
    }


    public static void test(String em) throws  Exception{
        if (em.matches(regex)) {
            System.out.println("这个邮箱符合规定");
        } else {
            throw new Exception("这个特邮箱不符合规定");
        }
    }

}
