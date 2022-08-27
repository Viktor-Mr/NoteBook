package day11.Object;



public class Oject_equals {
    public static void main(String[] args){
        Student s1 = new Student(1,"王朝");
        Student s2 = new Student(1,"王朝");
        Student s3 = s1;
        System.out.println("使用equals来比较对象---");
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));
        System.out.println("使用 == 来比较对象----");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);


    }
}
