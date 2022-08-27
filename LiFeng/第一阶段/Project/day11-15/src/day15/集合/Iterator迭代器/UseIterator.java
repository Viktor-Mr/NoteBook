package day15.集合.Iterator迭代器;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

public class UseIterator {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("计算机网络");
        list.add("操作系统");
        list.add("java编程思想");
        list.add("java核心技术");
        list.add("java语言程序设计");

        Iterator it = list.iterator();
        while(it.hasNext()){ //遍历迭代器
            String book = (String) it.next();
            System.out.println(book);
        }


    }
}
