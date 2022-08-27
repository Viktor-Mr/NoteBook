package day09.垃圾回收机制;

public class rb {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            R r = new R(i);
            r.m();
            r = null;
            System.gc();
        }

    }
//    public static void finalize(){
//        System.out.println("垃圾回收");
//    }


}

class R {
    static int age;
    String name;

    public R(int i){
        this.age = i;
    }
    @Override
    public void finalize() {
        System.out.println("垃圾回收" + age);
    }

    public  void m(){
        System.out.println(age);
    }
}


