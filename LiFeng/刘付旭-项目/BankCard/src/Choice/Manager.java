package Choice;

import DataVo.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    static Scanner sc = new Scanner(System.in);

    public static void menuManager() {
        System.out.println("-------------------------------------");
        System.out.println("        1. 账户资料管理(账户信用管理)");
        System.out.println("        2. 账户账单管理              ");
        System.out.println("        3. 账户欠款管理              ");
        System.out.println("        4. 返回上一级                ");
        System.out.println("-------------------------------------");
        System.out.print("请输入:");
        while (true) {
            int intput = sc.nextInt();
            switch (intput) {
                case 1:
                    AccountManager();
                    menuManager();
                    break;
                case 2:
                    BillManager();
                    UseClear.clear();
                    menuManager();
                    break;
                case 3:
                    ArrearageManager();
                    UseClear.clear();
                    menuManager();
                    break;
                case 4:
                    UseClear.clear();
                    Main.welcome();
                    break;
                default: {
                    System.out.println("请重新输入，正确的选项:");
                    break;
                }
            }
        }
    }

    //账户管理
    public static void AccountManager() {
        System.out.println("\n-----------------------------------------------------------------------------------");
        System.out.println("---  0.查看所有账号信息  1.查询某人账号信息   2. 增加     3. 修改      4. 删除      5. 返回上一级     ---");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.print("请输入:");
        while (true) {
            String intput = sc.next();
            switch (intput) {
                case "0":
                    upSee();
                    AccountManager();
                    break;
                case "1":
                    OneAccount();
                    AccountManager();
                    break;
                case "2":
                    upAdd();
                    AccountManager();
                    break;
                case "3":
                    upData();

                    AccountManager();
                    break;
                case "4":
                    upDel();
                    AccountManager();
                    break;
                case "5":
                    Manager.menuManager();
                    break;
                default: {
                    System.out.print("请重新输入，正确的选项:");
                    break;
                }
            }
        }

    }

    //账单管理
    public static void BillManager() {
        List<BillVo> listBillVo = Tool.getBillVO();
        if (listBillVo.size() == 0) {
            System.out.println("----------------------------------------------");
            System.out.println("*--------------银行系统无账单账单-------------*");
            System.out.println("----------------------------------------------");
        }
        System.out.println("\n账单号\t\t账号\t用户名\t\t金额\t\t类型");
        for (BillVo one : listBillVo) {
            System.out.println(one.getBillNo() + "\t" + one.getCardNo() + "\t" + one.getUserName() + "\t\t" + one.getValue() + "\t\t" + one.getType());
        }
        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //欠费（催款）管理
    public static void ArrearageManager() {
        List<AccountVo> listAccountVo = Tool.getAccountVO();
        if (listAccountVo.size() == 0) {
            System.out.println("-----------------------------------------------");
            System.out.println("*--------------银行系统无欠费用户-------------*");
            System.out.println("-----------------------------------------------");
        }
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("序号\t账号(卡号)\t用户名\t\t总信用额\t可用信用额\t可取现额\t欠款金额\t预存金额\t状态");

        int i = 0;
        int flag = 0;
        for (AccountVo one : listAccountVo) {
            if (one.getCreditower() > 0) {
                String able = String.format("%.1f", one.getCreditable());  //把  可用信用  额转化为尾数为两位的double类型
                String cash = String.format("%.1f", one.getCreditCash());  //把  可取现额  转化为尾数为两位的double类型
                String ower = String.format("%.1f", one.getCreditower());  //把  欠款  转化为尾数为两位的double类型

                System.out.println(i + "\t\t" + one.getCardNo() + "\t\t" + one.getUsername() + "\t\t" + one.getCreditTotal() + "\t\t" +
                        able + "\t\t" + cash + "\t\t" + ower + "\t\t" + one.getCreditPresent() + "\t\t" + one.getState());
                i++;
                flag = 1;
            }
            if (flag == 0) {
                System.out.println("----------------------------------无欠费用户----------------------------------------");
            }

        }
        System.out.println("----------------------------------------------------------------------------------------------\n");

    }

    //账户管理  增加用户
    public static void upAdd() {
        List<AccountVo> listAccount = Tool.getAccountVO();

        //构造方法为 姓名 卡号 密码
        System.out.println("----------------------------------------------------");
        System.out.println();
        System.out.print("增加的卡号:");
        String no = sc.next();
        System.out.print("增加的用户名:");
        String name = sc.next();
        System.out.print("增加的密码:");
        String pass = sc.next();

        System.out.print("是否新增账号 Y/N:");
        String answer = sc.next();

        int flag = 0 ; //判断是否有相同的账号
        for (AccountVo one : listAccount) {
            if (one.getCardNo().equals(no))
                flag = 1;
            if (one.getUsername().equals(name))
                flag = 1;
        }

        if (answer.trim().equals("y") || answer.trim().equals("Y") || answer.trim().equals("Yes")) {
            AccountVo newuser = new AccountVo(name, no, pass);
            listAccount.add(newuser);
        }

        if (flag == 1){
            System.out.println("存在相同的用户名 或者 相同的卡号，无法添加新用户 ");
        }
        else{
            Tool.setAccountVo(listAccount);
        }

    }

    //    //账户管理  删除用户的账单
    public static void upDelBill(String no) {
        List<BillVo> billVo = Tool.getBillVO();


        for (int i = 0; i < billVo.size(); i++) {
            if (billVo.get(i).getCardNo().equals(no)) {
                billVo.remove(i);
                i--;
            }
        }
        Tool.setBillVo(billVo);
    }

    //账户管理  删除用户
    public static void upDel() {
        List<AccountVo> listAccountVo = Tool.getAccountVO();
        System.out.println("\n----------------------------------------------------");
        System.out.print("请输入需要删除的卡号:");
        String no = sc.next();

        int flag = 0;
        for (int i = 0; i < listAccountVo.size(); i++) {
            if (listAccountVo.get(i).getCardNo().equals(no)) {
                upDelBill(no);
                listAccountVo.remove(i);
                flag = 1;

            }
        }
        if (flag == 0) {
            System.out.println("没有找到对应的卡号无法删除");
            System.out.print("是否继续输入Y/N");
            String answer = sc.next();
            if (answer.trim().equals("y") || answer.trim().equals("Y") || answer.trim().equals("Yes") || answer.trim().equals("YES"))
                upDel();
        }


        if (flag == 1) {
            Tool.setAccountVo(listAccountVo);
        }

    }

    //账户管理  修改用户信息
    public static void upData() {
        List<AccountVo> listAccount = Tool.getAccountVO();
        System.out.print("\n输入信息的卡号:");
        String no = sc.next();
        int flag = 0; //判断是否找到了账号
        for (int i = 0; i < listAccount.size(); i++) {
            if (listAccount.get(i).getCardNo().equals(no)) {
                flag = 1;

                System.out.println("1.用户名 2.总信用额 3.可用信用额 4.可取现额 5.欠款金额 6.预存金额 7.状态");
                while (true) {
                    System.out.print("请修改信息的选项");
                    String num = sc.next();
                    System.out.print("修改的数据:");
                    String data = sc.next();

                    switch (num) {
                        case "1":
                            listAccount.get(i).setUsername(data);
                            break;
                        case "2":
                            listAccount.get(i).setCreditTotal(Double.parseDouble(data));
                            break;
                        case "3":
                            listAccount.get(i).setCreditable(Double.parseDouble(data));
                            break;
                        case "4":
                            listAccount.get(i).setCreditCash(Double.parseDouble(data));
                            break;
                        case "5":
                            listAccount.get(i).setCreditower(Double.parseDouble(data));
                            break;
                        case "6":
                            listAccount.get(i).setCreditPresent(Double.parseDouble(data));
                            break;
                        case "7":
                            listAccount.get(i).setState(Integer.parseInt(data));
                            break;
                        default: {
                            System.out.print("请重新输入，正确的选项:");
                            break;
                        }
                    }
                    System.out.print("是否继续修改 " + listAccount.get(i).getCardNo() + " 的内容 Y/N:");
                    String answer = sc.next();
                    if (answer.trim().equals("n") || answer.trim().equals("No") || answer.trim().equals("N") || answer.trim().equals("no") || answer.trim().equals("NO")) {
                        break;
                    }
                }
            }
        }
        Tool.setAccountVo(listAccount);

        if (flag == 0) {
            System.out.println("无法查询到账号");
        }
    }

    public static void upSee() {
        List<AccountVo> listAccount = Tool.getAccountVO();
        System.out.println("序号\t账号(卡号)\t用户名\t\t总信用额\t\t可用信用额\t\t可取现额\t\t欠款金额\t\t预存金额\t\t状态");

        int i = 0;

        for (AccountVo one : listAccount) {
            String able = String.format("%.2f", one.getCreditable());  //把  可用信用  额转化为尾数为两位的double类型
            String cash = String.format("%.2f", one.getCreditCash());  //把  可取现额  转化为尾数为两位的double类型
            String ower = String.format("%.2f", one.getCreditower());  //把  欠款  转化为尾数为两位的double类型


            System.out.println(i + "\t\t" + one.getCardNo() + "\t\t" + one.getUsername() + "\t\t" + one.getCreditTotal() + "\t\t" +
                    able + "\t\t\t" + cash + "\t\t\t" + ower + "\t\t\t" + one.getCreditPresent() + "\t\t\t" + one.getState());
            i++;
        }
    }

    public static  void OneAccount(){
        System.out.print("请输入你需要查找的账户...卡号:");
        String no  = sc.next();
        User.MyAccount(no);
    }
}