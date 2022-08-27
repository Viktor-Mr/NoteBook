package day11.Object;

public class TestObject_getClass {
    public static void main(String[] args){
        Student stu  = new Student();
        String test = stu.getClass().getName();
        System.out.println(test);
    }
}
