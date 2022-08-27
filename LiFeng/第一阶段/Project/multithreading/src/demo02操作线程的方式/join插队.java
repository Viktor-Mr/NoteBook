package demo02操作线程的方式;

public class join插队 {

    static Music04 music02 =new Music04();
    static Thread t_music =new Thread(music02);

    static Read04 read02 = new Read04();
    static Thread t_read  = new Thread(read02);

    public static void main(String[] args) {





        t_music.start();
        t_read.start();




    }
}
class Music04 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i + "在听音乐");
        }
    }
}
class Read04 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            if(i==2) {
                try {
                    join插队.t_music.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(i + "在玩游戏");
        }
    }
}
