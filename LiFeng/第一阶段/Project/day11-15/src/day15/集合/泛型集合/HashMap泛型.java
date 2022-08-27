package day15.集合.泛型集合;

import day15.集合.ArrayList动态.Student;
import day15.集合.ArrayList动态.Teacher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMap泛型 {
    public static void main(String[] args) {
        Map<String, Student>students  = new HashMap<String, Student>();
        //创建学生对象
        Student stu1 = new Student("张无忌",18);
        Student stu2 = new Student("李寻欢",21);
        Student stu3 = new Student("李白",22);
        Student stu4 = new Student("白居易",22);
        //创建教师对象
        Teacher teal = new Teacher("张三","教师");
        //添加元素
        students.put("101",stu1);
        students.put("102",stu2);
        students.put("103",stu3);
        students.put("104",stu4);
//      students.put("105",teal);//类型不匹配无法存入
        Student student = students.get("102"); //无须强转
        System.out.println(student.getAge() + " " + student.getName());

        Set<String> set = students.keySet();
        System.out.println("------遍历1--------");
        for (String key:set){
            Student value = students.get(key);
            System.out.println(key + " " + value.getName() + " " + value.getAge());
        }

        Collection<Student>  studs = students.values();
        System.out.println("-------遍历2---------");
        for (Student stu:studs){
            System.out.println(stu.getAge() + " " + stu.getName() + " " + stu);
        }

        System.out.println("-------遍历3 ---------");
        Set<Map.Entry<String,Student>> stus = students.entrySet(); //返回键值对的集合
        for(Map.Entry entry:stus){
            System.out.println(entry.getKey() + " |||" + entry.getValue());
        }

    }
}
