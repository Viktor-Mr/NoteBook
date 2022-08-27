package demo03线程的同步;
//非静态的同步方法的锁对象是神马?
//答:非静态的同步方法的锁对象是this
//静态的同步方法的锁对象是什么? :字节码文件
//是该类的字节码对象


public class sync同步 {
    public static void main(String[] args) {
       Print02 print = new Print02();
        new Ticket(){
            @Override
            public void run(){
                while (true){
                    print.print01();
                }
            }
        }.start();

        new Ticket(){
            @Override
            public void run(){
                while (true){
                    print.print02();
                }
            }
        }.start();
    }
}

class  Print01{

    public static synchronized void print01(){
            System.out.print("春");
            System.out.print("眠");
            System.out.print("不");
            System.out.print("觉");
            System.out.print("晓\n");

    }

    public   void print02(){
        synchronized (Print01.class){
            System.out.print("阿");
            System.out.print("撸");
            System.out.print("吧\n");

        }
    }

}



class  Print02{

    public  synchronized  void print01(){
        System.out.print("春");
        System.out.print("眠");
        System.out.print("不");
        System.out.print("觉");
        System.out.print("晓\n");

    }

    public   void print02(){
        synchronized (this){
            System.out.print("阿");
            System.out.print("撸");
            System.out.print("吧\n");

        }
    }

}

class  Print{

    public static void print01(){
        synchronized (Print.class){
            System.out.print("春");
            System.out.print("眠");
            System.out.print("不");
            System.out.print("觉");
            System.out.print("晓\n");
        }
    }

    public   void print02(){
        synchronized (Print.class){
            System.out.print("阿");
            System.out.print("撸");
            System.out.print("吧\n");

        }
    }
}
