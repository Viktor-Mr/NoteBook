package day15.集合.ArrayList动态;

import java.util.ArrayList;
import java.util.List;

public class 使用集合UseObject {
    public static void main(String[] args) {
        Student stu1 = new Student("张无忌",18);
        Student stu2 = new Student("李寻欢",19);
        Student stu3 = new Student("小龙女",20);
        Student stu4 = new Student("郭晓峰",21);

        ArrayList student = new ArrayList();
        System.out.println("-----添加学生前----");
        System.out.println("当前长度:" + student.size());
        student.add(stu1);
        student.add(stu2);
        student.add(stu3);
        student.add(stu4);

        System.out.println("-----添加学生后----");
        System.out.println("当前长度:" + student.size());
        Student stu = (Student)student.get(0);//get返回类型是object类型，必须强制转化为Student

        System.out.println(student);
        System.out.println("-----遍历所有学生-----");
        for (int i = 0; i <student.size() ; i++) {
            //Student stud = (Student)student.get(i);
            System.out.println( student.get(i));
        }

    }


}
