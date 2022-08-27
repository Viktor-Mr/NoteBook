package day15.集合.Foreach循环;

import java.util.ArrayList;

public class UseForeach {
    public static void main(String[] args) {
        ArrayList list  = new ArrayList();
        list.add("苹果");
        list.add("香蕉");
        list.add("西瓜");
        list.add("葡萄");
        for(Object object : list){
            System.out.println(object); //输出集合中的元素
        }

        String []fruits = {"t1","t2","t3"};
        for(String fruit : fruits){
            System.out.println(fruit); //输出集合中的元素
        }
    }
}
