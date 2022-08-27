package day15.集合.LinkedList集合;

import java.util.LinkedList;
import java.util.List;

public class LinkedList链表使用 {
    public static void main(String[] args) {
        //创建LinkedList 集合
        LinkedList linkedList = new LinkedList();
        // 添加元素
        linkedList.add("学生1");
        linkedList.add("学生2");
        linkedList.add("学生3");
        linkedList.add("学生4");
        System.out.println(linkedList); //输出集合中的元素
        linkedList.offer("后面添加的学生"); //向集合尾部添加元素
        linkedList.push("前面添加的学生"); //向集合头部添加元素
        System.out.println(linkedList);
        System.out.println(linkedList.get(1));
        //获取元素
        Object object = linkedList.peek(); //获取集合的第一个元素
        System.out.println(object); //输出集合的第一个元素
        System.out.println(linkedList);

        object =  linkedList.peekFirst(); //获取集合第一个元素
        System.out.println(object);
        System.out.println(linkedList);

        //删除元素
        linkedList.remove(); //删除集合第一个元素
        linkedList.pollLast();//删除集合最后一个元素
        System.out.println(linkedList);

        linkedList.offerLast("stu");
        System.out.println(linkedList);
    }
}
