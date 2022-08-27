package firsthomework;

public class task003 {
    public static void main(String[] args) {
        Task04 demo = new Task04();


        new Thread(){
            @Override
            public void run(){
                while (true)
               demo.demo01();
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
               while (true){
                   demo.demo02();
               }
            }
        }.start();



    }
}

class  Task04 {
    public  synchronized void demo01(){
        System.out.print("百");
        System.out.print("日");
        System.out.print("依");
        System.out.print("山");
        System.out.print("尽\n");
    }
    public synchronized void  demo02(){
        System.out.print("恭");
        System.out.print("喜");
        System.out.print("发");
        System.out.print("彩\n");
    }
}

