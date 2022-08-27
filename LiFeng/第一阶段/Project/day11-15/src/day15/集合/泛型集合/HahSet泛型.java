package day15.集合.泛型集合;

import java.util.HashSet;
import java.util.Set;


public class HahSet泛型 {
    public static void main(String[] args) {
        //创建了一个Set的集合对象
        Set<Student> set = new HashSet<Student>();
        Student stu1  =new Student("张无忌",18);
        Student stu2  =new Student("李寻欢",21);
        Student stu3  =new Student("李白",22);
        Student stu4  =new Student("张三",23);
        Teacher tea1  =new Teacher("张三","教室");
        set.add(stu1);
        set.add(stu2);
        set.add(stu3);
        set.add(stu4);
        //set.add(tea1); //教师类型无法存入这个集合
        System.out.println("------------遍历----------");
        for(Student a:set){ //无需强转类型
            System.out.println(a);
        }
    }
}
