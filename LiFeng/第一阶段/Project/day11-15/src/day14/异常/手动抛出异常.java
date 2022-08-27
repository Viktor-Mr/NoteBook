package day14.异常;

public class 手动抛出异常 {
    public static void main(String[] args) {
        try {   //手动抛出异常，传递参数时，传递是地址
                // 两者命名可以不一样
            int age = 1000;
            if(age>120){
                Exception e1 = new Exception("年龄不合理");
                throw  e1; //手动抛出异常
            }
            System.out.println("测试");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
