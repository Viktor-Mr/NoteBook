package day08.类变量与实际变量;

public class ClassJava{
    public static void main(String[] args){
        person A=new person();
        A.age=10;
        A.name="张三";
        System.out.println(A.age);  //-结果为"10"
        System.out.println(A.name); //-结果为"张三"
        person B=new person();
        B.age = 100;

        //类变量是针对所有对象的，所以A改变age，B的age也改变
        System.out.println(B.age);  //-结果为"10"
        //实例只改变自身的，所以A对象的name改变，不影响对象B的name变量
        System.out.println(B.name); //-结果仍为"李四"


        System.out.println(A.age);  //-结果为"10"
        System.out.println(A.name); //-结果为"张三"
    }
}

