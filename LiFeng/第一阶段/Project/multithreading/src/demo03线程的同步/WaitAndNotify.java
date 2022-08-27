package demo03线程的同步;

public class WaitAndNotify {
    public static void main(String[] args) {
        final Printer p = new Printer();

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        p.print1();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        p.print2();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        p.print3();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}



//等待唤醒机制
class Printer {
    private int flag = 1;

    public void print1() throws InterruptedException {
        synchronized (this) {
            if(flag !=1 )
                wait();
            if(flag==1){
            System.out.print("春");
            System.out.print("眠");
            System.out.print("不");
            System.out.print("觉");
            System.out.print("晓");
            System.out.print("\r\n");
            flag = 2;}
            this.notifyAll();
        }
    }

    public void print2() throws InterruptedException {
        synchronized (this) {
            if(flag!=2)
                wait();
            if(flag==2){
            System.out.print("砺");
            System.out.print("锋");
            System.out.print("科");
            System.out.print("技");
            System.out.print("\r\n");
            flag =3 ;}
            this.notifyAll();
        }
    }
    public void print3() throws InterruptedException {
        synchronized (this) {
            if(flag!=3)
                wait();
            if(flag==3){
            System.out.print("花");
            System.out.print("落");
            System.out.print("知");
            System.out.print("多");
            System.out.print("少\r\n\n");
            flag =1 ;}
            this.notifyAll();
        }
    }
}

