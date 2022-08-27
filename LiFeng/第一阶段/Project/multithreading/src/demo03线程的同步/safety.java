package demo03线程的同步;

public class safety {

    public static void main(String[] args) {
        Ticket t1 = new Ticket();
        t1.start();
        new Ticket().start();
        new Ticket().start();
        new Ticket().start();

    }
}

class Ticket extends Thread{
    private static int ti = 100;
    @Override
    public void run(){
        while(true){
            synchronized (Ticket.class){
            if (ti == 0)
                break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getName()+"卖出第" +  ti-- + "张票");
            }
        }
    }
}
