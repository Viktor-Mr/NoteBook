package 代理模式;

                             // 我
/*  小红的作业                代理人                   老板*/

public class test {
    public static void main(String[] args) {
        //小红自己做作业
//        HomeWork herself  = new GoHome();
//        herself.doing("数学");


        HomeWork proxy  = new studentProxy(new GoHome());
        proxy.doing("英语");
    }
}
interface HomeWork{
    public  void doing(String subject);
}

class GoHome implements HomeWork{
    @Override
    public void doing(String subject) {
        System.out.println("完成了这份"+ subject+"作业");
    }
}

class studentProxy implements HomeWork{
    GoHome goHome;

    public studentProxy(GoHome goHome) {
        this.goHome = goHome;
    }

    @Override
    public void doing(String subject) {
        goHome.doing(subject);
    }
}
