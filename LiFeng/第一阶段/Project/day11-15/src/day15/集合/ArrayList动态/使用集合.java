package day15.集合.ArrayList动态;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class 使用集合 {
    public static void main(String[] args) {

        System.out.println("ArrayList集合的定义方式");
        ArrayList list = new ArrayList(); //方法一
        //List list = new ArrayList();    //方法二

        System.out.println("------计算长度集合---------");
        System.out.println(list.size());

        System.out.println("------添加元素---------");
        list.add("学生4");//下标为0
        list.add("学生2");//下表为1
        list.add("学生3");//下标为2
        System.out.println(list.size());



         Collections.sort(list); //排序
        System.out.println("------集合的遍历---------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((String) list.get(i));
        }

//
//        System.out.println("------利用下标取出集合中的元素---------");
//        System.out.println(list.get(1));
//        String student1 = (String) list.get(1);
//        System.out.println(student1);
//
//
//        System.out.println("------集合的遍历---------");
//        for (int i = 0; i <list.size() ; i++) {
//            System.out.println((String)list.get(i));
//        }
//
//        System.out.println("-----插入的下标 在索引为0处插入----");
//        list.add(0,"张无忌是第一个同学");
//        for (int i = 0; i <list.size() ; i++) {
//            System.out.println((String)list.get(i));
//        }
//
//        System.out.println("----插入的下标 在索引为0处修改-----");
//        list.set(1,"张无忌2是第二个同学");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println((String)list.get(i));
//        }
//
//        System.out.println("-----查找元素(返回Boolean)-----");
//        System.out.println(list.contains("学生3"));//true
//
//        System.out.println("-----删除元素(可以利用下标/或者利用 元素值)--------");
//        list.remove(2);
//        list.remove("张无忌2");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println((String)list.get(i));
//        }
//        System.out.println("-----清空集合--------");
//        list.clear();
//        System.out.println(list.size());
//        System.out.println(list.isEmpty()); //判断集合是否为空
    }
}
