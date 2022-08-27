package demo01实现多线程的方式;

public class threadClass {
    public static void main(String[] args) {
        Music music =new Music();
        Read read = new Read();

//        for (int i = 0; i <1000 ; i++) {
//            System.out.println(i + "看星星");
//        }
        //music.start();
        //read.start();
        System.out.println(music.getName());
        System.out.println(read.getName());//获取线程的名字

        music.setName("one");
        read.setName("two");

        System.out.println(music.getName());
        System.out.println(read.getName());//获取线程的名字

    }
}
class Music extends Thread{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在听音乐");
        }
    }
}
class Read extends Thread{
    @Override
    public void run(){
        for (int i = 0; i <1000 ; i++) {
            System.out.println(i + "在玩游戏");
        }
    }
}

