package demo03线程的同步;

public class LockProblem {
    public static void main(String[] args) {
        Sales sales = new Sales();
        sales.start();
        Buyer buyer = new Buyer();
        buyer.start();
    }

}

class Product{}
class Money{}

class  Sales extends Thread{

    @Override
    public void run(){
        synchronized (Product.class){   // 1->0
            System.out.println("货物在手，等待买家付款");

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Money.class){
                System.out.println("拿到钱，交货  ");
            }
        }
    }
}

class  Buyer extends Thread{
    @Override
    public void run(){
        synchronized (Money.class){   //1 ->  0
            System.out.println("钱在手，等待卖家交货");

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Product.class){
                System.out.println("拿到货物，付款  ");
            }
        }
    }
}