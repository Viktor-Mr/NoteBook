package day15.集合.泛型集合;





import java.util.ArrayList;
import java.util.List;


public class ArrayList泛型 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();//定义了一个String类型的泛型集合
        list.add("孙悟空");
        list.add("奥特曼");
        //list.add(007); //这个存储不进去出现编译时异常
        list.add("007"); //必须要用String类型才可以

        //查找单个元素
        String hero = list.get(2);
        System.out.println(hero);

        //遍历
        for (String obj:list){
            System.out.println(obj);//遍历泛型集合时无需强制转换
        }

        System.out.println("-----------------------------");
        //创建四个学生对象
        Student stu1 = new Student("张无忌",18);
        Student stu2 = new Student("李寻欢",21);
        Student stu3 = new Student("白部曲",22);
        Student stu4 = new Student("考你老",22);
        List<Student> students = new ArrayList<Student>();//创建Student类型的泛型集合

        //添加学生对象元素到ArrayList集合
        students.add(stu1);
        students.add(stu2);
        students.add(stu3);
        students.add(stu4);
        Teacher teacher = new Teacher("张三","教室");
      //  students.add(teacher);
        System.out.println("集合的长度是:" + students.size());

        //查找单个元素
        Student stu = students.get(2);  //无需强转
        System.out.println("学生姓名:" + stu.getName()+ ", 学生年龄:" +stu.getAge());
        System.out.println("---------------------");

        // 遍历
        for(Student student:students){ //同样遍历时也无须强转
            //不会再出现类型转换异常了,因为泛型集合中只有Student 类型
            System.out.println(student.getAge() + " " + student.getName());
        }

    }
}
