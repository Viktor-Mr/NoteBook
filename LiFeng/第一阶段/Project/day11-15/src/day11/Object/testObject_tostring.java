package day11.Object;

public class testObject_tostring {
    public static void main(String[] args){
        Student stu = new Student();
        System.out.println("stu的地址 " + stu);
        System.out.println("stu的地址 " + stu.toString());
        System.out.println("stu的地址 " + stu.getClass().getName() + "@" + Integer.toHexString(stu.hashCode()));
    }
}
