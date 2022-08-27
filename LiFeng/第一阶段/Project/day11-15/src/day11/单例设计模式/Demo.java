package day11.单例设计模式;
/*    某一个类比较特殊，他并不需要被反复创建，所有人都共享这个堆区的数据即可。
    如果这个类被反复的实例化，将会对空间内存造成极大的浪费。

    这个类只能被实例化一次，且所有人共享即可。

    这种方式就被称之为单例设计模式:
        1.我不能让别人创建对象(构造私有化)
        2.既然别人不能创建对象了，那这么保证一次实例
        3.写一个方法，返回当前类对象即可，这个方法必须是静态的 否则人家无法调用
        4.为了保证人家访问的是同一个对象，所以在成员位置创建了一个类对象，且是私有和静态的
        5.在写好的方法里面，将成员位置的类对象进行返回即可
*/




public class Demo {
    public static void main(String[] args) {
        Singleton01 s1 = Singleton01.getSingleton();
        System.out.println(s1);
        Singleton01  s2 = Singleton01.getSingleton();
        System.out.println(s2);
        System.out.println(Singleton02.getSingleton());
        System.out.println(Singleton02.getSingleton());
    }
}


/*饿汉式单例设计模式*/
class Singleton01{
    private static Singleton01 s = new Singleton01();

    private Singleton01(){

    }
    public static  Singleton01 getSingleton(){
        return s;
    }

}

/*懒汉式单例设计模式*/
class Singleton02{
    private static Singleton02 s = null;

    private Singleton02(){
    }

    public static  Singleton02 getSingleton(){
        if (s == null){
            s = new Singleton02();
        }
        return  s;
    }
}