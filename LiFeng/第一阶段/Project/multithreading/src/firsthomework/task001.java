package firsthomework;

public class task001 {
    public static void main(String[] args) {
        Demo01 demo01 = new Demo01("张三");
        demo01.start();

        Demo02 demo02  = new Demo02();
        Thread thread =  new Thread(demo02,"李四");
        thread.start();

        new Thread("王五"){
            @Override
            public void run(){
                for (int i = 0; i <1000 ; i++) {
                    System.out.println( i + getName() +  "在上网");
                }
            }
        }.start();
    }
}

class Demo01 extends Thread{
    public Demo01(String s) {
        this.setName(s);
    }
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println( i + getName() + "在玩游戏");
        }
    }
}

class Demo02 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println( i+ Thread.currentThread().getName() +  "在听音乐");
        }
    }
}

