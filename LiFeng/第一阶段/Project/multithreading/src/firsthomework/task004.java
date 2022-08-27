package firsthomework;

public class task004 {
    public static void main(String[] args) {
        new Room("途牛").start();
        new Room("半夜友情").start();
        new Room("瓦特").start();


    }
}


class Room extends Thread {
    private static int num = 100;
    public Room(String s){
        this.setName(s);
    }
    @Override
    public void run(){
        while(true){
            synchronized (Room.class){
            if(num==0)
                break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + "拿到"+  (num--)+ "号房间");
            }
        }

    }
}
