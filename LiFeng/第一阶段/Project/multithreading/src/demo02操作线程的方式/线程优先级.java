package demo02操作线程的方式;

public class 线程优先级 {
    public static void main(String[] args) {
        Music03 music02 =new Music03();
        Thread t_music =new Thread(music02);

        Read03 read02 = new Read03();
        Thread t_read  = new Thread(read02);


//        t_music.setPriority(1); //优先级小
//        t_read.setPriority(10); //优先级大

        t_music.setPriority(Thread.MIN_PRIORITY);   //优先级大
        t_read.setPriority(Thread.MAX_PRIORITY);      //优先级小

        t_music.start();
        t_read.start();



//        System.out.println(   t_music.getName());
//        System.out.println(   t_read.getName());
//
//        t_music.setName("test_one");
//        t_read.setName("test_two");
//
//
//        System.out.println(   t_music.getName());
//        System.out.println(   t_read.getName());


    }
}
class Music03 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在听音乐");
        }
    }
}
class Read03 implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在学习");
        }
    }
}
