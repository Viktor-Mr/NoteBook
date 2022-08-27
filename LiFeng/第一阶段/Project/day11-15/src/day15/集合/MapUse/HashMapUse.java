package day15.集合.MapUse;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapUse {
    public static void main(String[] args) {
       //创建HashMap对象
        HashMap map = new HashMap();
        map.put("1","张无忌");
        map.put("6","李寻欢");
        map.put("3","韦小宝");
        map.put("4","韦小宝");
        map.put("1","李白");
        System.out.println(map);
        //查看键值对是否存在
        System.out.println(map.containsKey("1"));
        //获取指定键值对的映射值
        System.out.println(map.get("1"));
        //获取集合中键对象和值对象集合
        Set keys = map.keySet();
        System.out.println(keys);
        Collection values = map.values();
        System.out.println(values);

        //替换指定键值对的值
        map.replace("4","大佬");
        //删除指定键值对的元素
        map.remove("1");
        System.out.println(map);
        System.out.println(map.size());
    }
}
