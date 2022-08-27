package demo02操作线程的方式;

public class yield让步 {
    public static void main(String[] args) {
        Music1 music =new Music1();
        Read1 read = new Read1();

//        for (int i = 0; i <1000 ; i++) {
//            System.out.println(i + "看星星");
//        }

        music.setPriority(1);
        read.setPriority(10);


        music.start();
        read.start();

    }
}
class Music1 extends Thread{
    @Override
    public void run(){
        for (int i = 0; i <100 ; i++) {
            System.out.println(i + "在听音乐");
        }
    }
}
class Read1 extends Thread{
    @Override
    public void run(){
        for (int i = 0; i <100 ; i++) {
            if(i==10)
                Thread.yield();
            System.out.println(i + "在玩游戏");
        }
    }
}
