package day10.Super;

public class SuperDome {
    public static void main(String[] args){
        Student s1 = new Student();
        s1.show();
        System.out.println("----------------------------");
        System.out.println(s1.name);
        System.out.println(s1.gender);
        System.out.println(s1.age);
        System.out.println("-----------------------------");
        Student s2 = new Student("张无忌","男",19);
        System.out.println(s2.name);
        System.out.println(s2.gender);
        System.out.println(s2.age);
    }
}
