package day14.异常.自定义异常;

import java.util.Scanner;

public class TestStudent {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student s1 = new Student();
        System.out.print("请输入学生性别:");
        String gender = sc.next();
        System.out.print("请输学生年龄:");
        int age = sc.nextInt();

        try {
            s1.setAge(age);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            s1.setGender(gender);
        } catch (GenderException e) {
            System.out.println(e.getMessage());
        }
        s1.setName("张无忌");
        System.out.println(s1.toString());
    }
}
