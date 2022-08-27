package Choice;

import DataVo.AccountVo;
import DataVo.Tool;

import DataVo.*;

import java.awt.*;
import java.util.*;
import java.util.List;


//主页面启动
public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //先初始化一个数据  调用对象输入流   输入账号信息的集合
        List<AccountVo> listAccount = Tool.getAccountVO();
        //构造方法为 姓名 卡号 密码

        if (listAccount.size() ==0) {
            AccountVo user1 = new AccountVo("user01", "00001", "1231");
            AccountVo user2 = new AccountVo("user02", "00002", "1232");
            AccountVo user3 = new AccountVo("user03", "00003", "1233");
            listAccount.add(user1);
            listAccount.add(user2);
            listAccount.add(user3);
            Tool.setAccountVo(listAccount);
        }

        welcome();

    }//方法结尾

    public static void welcome() {

        System.out.println("--------------------------------------------");
        System.out.println("        欢迎来到信用卡管理系统              ");
        System.out.println("            1.用户登录                      ");
        System.out.println("            2.银行管理员登录                ");
        System.out.println("            3.退出                          ");
        System.out.println("---------------------------------------------\n");
        System.out.print("请输入:");

        while (true) {
            String intput = sc.next();
            switch (intput) {
                case "1":
                    LoginUser();
                    break;
                case "2":
                    LoginManager();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.print("输入错误请重新输入:");
            }
        }
    }//方法结尾


    public static void LoginManager() {
        System.out.println("---------------------------------------------");

        System.out.print("管理员名称:");
        String managerName = sc.next();
        System.out.print("密   码 :");
        String managerPass = sc.next();
        while (true) {


            if (managerName.trim().equals("admin")) {
                if (managerPass.trim().equals("123")) {
                    System.out.println("登录成功");
                    Manager.menuManager();
                    break;
                }
            }
            System.out.print("信息核对失败是否需要进行再次输入Y/N");
            String newgoon = sc.next();
            if (newgoon.equals("N") || newgoon.equals("No")|| newgoon.equals("n") || newgoon.equals("NO")) {
                welcome();
                break;
            }
            System.out.print("\n管理员名称:");
            managerName = sc.next();
            System.out.print("密   码 :");
             managerPass = sc.next();
        }

    }//方法结尾

    public static void LoginUser() {
        // 提出数据
        List<AccountVo> listAccount = Tool.getAccountVO();
        System.out.println("---------------------------------------------");

        System.out.print("用户名称:");
        String userName = sc.next();
        System.out.print(" 密   码 :");
        String userPass = sc.next();

        while (true) {
            for (AccountVo oneAccount : listAccount) {
                if (oneAccount.getUsername().equals(userName)) {
                    if (oneAccount.getPassWord().equals(userPass)) {
                        System.out.println("登录成功");
                        User.menuUser(oneAccount.getCardNo());
                        break;
                    }
                }
            }
            System.out.print("信息核对失败是否需要进行再次输入Y/N:");
            String newgoon = sc.next();
            if (newgoon.equals("N") || newgoon.equals("No")  || newgoon.equals("NO")|| newgoon.equals("n")){
                welcome();
                break;
            }
            System.out.print("\n用户名称:");
            userName = sc.next();
            System.out.print(" 密   码 :");
             userPass = sc.next();
        }

    }//方法结尾
}//类结尾



