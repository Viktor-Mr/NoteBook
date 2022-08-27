package demo01实现多线程的方式;

public class runnableInterface {
    public static void main(String[] args) {
        Music02 music02 =new Music02();
        Thread t_music =new Thread(music02);

        Read02 read02 = new Read02();
        Thread t_read  = new Thread(read02);

//        t_music.start();
//        t_read.start();

        System.out.println(   t_music.getName());
        System.out.println(   t_read.getName());

        t_music.setName("test_one");
        t_read.setName("test_two");


        System.out.println(   t_music.getName());
        System.out.println(   t_read.getName());


    }
}
class Music02 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在听音乐");
        }
    }
}
class Read02 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在玩游戏");
        }
    }
}