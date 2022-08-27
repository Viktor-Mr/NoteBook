package firsthomework;

public class task02 {
    public static void main(String[] args) {
        new Num().start();
        new Letter().start();
//        new Thread() {
//            @Override
//            public void run() {
//                synchronized (this) {
//                    for (int i = 65; i < 65 + 26; i++) {
//                        try {
//                            sleep(200);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        System.out.print((char) i + "\t");
//                    }}}}.start();
//
//        new Thread() {
//            @Override
//            public void run() {
//                synchronized (this) {
//                    for (int i = 1; i <= 26; i++) {
//                        try {
//                            sleep(200);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        System.out.print(i + "\t");
//
//                    }
//                }
//            }
//        }.start();
    }
}

    class Money {
    }


    class Num extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 26; i++) {
                synchronized (Money.class) {
                    System.out.print(i + "\t");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Money.class.notify();
                    try {
                        Money.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    class Letter extends Thread {
        @Override
        public void run() {
            for (int i = 65; i < 65 + 26; i++) {
                synchronized (Money.class) {
                    System.out.print((char) i + "\t");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Money.class.notify();
                    try {
                        Money.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


