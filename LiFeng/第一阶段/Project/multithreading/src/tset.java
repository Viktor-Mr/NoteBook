public class tset {
    public static void main(String[] args) {
        new Room().start();
        new Room().start();
        new Room().start();
        new Room().start();

    }
}

class Room extends Thread {
    private static int room = 100;

    public void run() {

            while (true) {
                synchronized (Room.class) {
                if (room == 0) {
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "已预订" + (100 - room--) + "号房");

           }
        }
    }
}
