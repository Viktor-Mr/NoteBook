package day15.集合.SetUse;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HashSetUse {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        //HahSet  set = new HashSet();
        set.add("beijing");
        set.add("shanghai");
        set.add("shenzhen");
        set.add("guangz");
        set.add(100);
        for (Object object: set) {
            System.out.print(object + " ");
        }
    }
}
