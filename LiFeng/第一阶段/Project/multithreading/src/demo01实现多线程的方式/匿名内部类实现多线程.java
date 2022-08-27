package demo01实现多线程的方式;

public class 匿名内部类实现多线程 {
    public static void main(String[] args) {
        Thread  test =  new Thread(){
            @Override
            public void run(){
//                for (int i = 0; i <1000 ; i++) {
//                    System.out.println(i + "在玩游戏");
//                }
                System.out.println(this.getName());
            }
        };

        test.start();

        test = new Thread( new Runnable() {
            @Override
            public void run(){
//                for (int i = 0; i <1000 ; i++) {
//                    System.out.println(i + "在听音乐");
//                 }
                System.out.println(Thread.currentThread().getName()); //获取当前线程的名字
            }
        });
        test.start();
        new Thread(){
            @Override
            public void run(){
//                for (int i = 0; i <1000 ; i++) {
//                    System.out.println(i + "在听音乐");
//                }
            }
        }.start();

    }
}
