package day11.Object;

public class TestObject_hashCode {
    public static  void main(String[] args){
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = s1;
        System.out.println("测试hashcode：" + s1.hashCode());
        System.out.println("测试hashcode：" + s2.hashCode());
        System.out.println("测试hashcode：" + s3.hashCode());
    }
}
