package demo02操作线程的方式;

public class sleep睡眠 {
    public static void main(String[] args) {
       Demo demo = new Demo();
       demo.start();
    }
}

class  Demo extends Thread{
    @Override
    public void run(){
        for (int i=10; i>1; i--) {
            System.out.println("倒计时" + i + "秒");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}