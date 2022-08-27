package firsthomework;

public class task002 {
    public static void main(String[] args) {
        DemoT002 demo = new DemoT002();
        Thread thread = new Thread(demo);
        thread.start();
    }
}

class DemoT002 implements Runnable{
    public static void demo(){
    }
    @Override
    public void run(){
        for (int i = 10; i>0 ; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("圣诞倒数 " + i );
        }
        System.out.println("Yeah!");
    }
}
