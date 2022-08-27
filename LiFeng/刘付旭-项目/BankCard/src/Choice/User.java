package Choice;

import DataVo.AccountVo;
import DataVo.BillVo;
import DataVo.Tool;
import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import sun.util.resources.cldr.CalendarData;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;


public class User {
    static Scanner sc = new Scanner(System.in);

    public static void menuUser(String no) {
        System.out.println("-----------------------------------");
        System.out.println("     1. 消费       ");
        System.out.println("     2. 还款     ");
        System.out.println("     3. 取现     ");
        System.out.println("     4. 账单管理     ");
        System.out.println("     5. 我的账户     ");
        System.out.println("     6. 返回上一级        ");
        System.out.println("-----------------------------------");
        System.out.print("请输入: ");
        while (true) {
            String intput = sc.next();
            switch (intput) {
                case "1":  //消费
                    Consume(no);
                    menuUser(no);
                    break;
                case "2":   //还款
                    Refund(no);
                    menuUser(no);
                    break;
                case "3":   //取现
                    Cash(no);
                    menuUser(no);
                    break;
                case "4":   //账单管理
                    MyBill(no);
                    UseClear.clear();
                    menuUser(no);
                    break;
                case "5":   //我的账户
                    MyAccount(no);
                    UseClear.clear();
                    menuUser(no);
                    break;
                case "6":
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

    // 账单生成---  主要负责、消费、还款、取现的账单生成
    public static void billNew(String userName, String cardNo, double value, int type) {

        //获得账单的时间
        Calendar t = Calendar.getInstance();
        String billTime = "p" + t.get(Calendar.YEAR) + (t.get(Calendar.MONTH) + 1) + t.get(Calendar.DATE) + (int) (Math.random() * t.get(Calendar.MINUTE));

        //取出txt文件中的账单信息
        List<BillVo> listBillVo = Tool.getBillVO();
        if (listBillVo == null) {
            listBillVo = new LinkedList<>();
        }

        //根据前面传来的 姓名 卡号 、、、 还有一个新生成的日期 建成一个集合
        BillVo bill = new BillVo(billTime, userName, cardNo, value, type);

        //再把集合加入到列表
        listBillVo.add(bill);

        //把列表塞入文件
        Tool.setBillVo(listBillVo);

    }

    /**
     * 消费时  还款时  取现时
     * creditTotal;        总信用额
     * creditTable;        可用信用额
     * creditCash;        可取现额度
     * creditPresent;      预存金额
     * creditower ;       欠款
     * <p>
     * 总信用额=可用信用额+预存金额
     * <p>
     * 可取现金额=可用信用额/1.1
     */


    //消费
    public static void Consume(String no) {
        System.out.println("信息核对中*********");
        List<AccountVo> listAccountVo = Tool.getAccountVO();

        for (AccountVo one : listAccountVo) {
            if (one.getCardNo().equals(no)) {

                //判断是否能进行消费
                if (one.getState() == 0) {
                    System.out.println("抱歉,卡号为" + one.getCardNo() + "的信用卡被冻结，请及时联系用户工作人员");
                } else {
                    System.out.print("本次消费的金额为(\\元):");
                    double num = sc.nextInt();
                    billNew(one.getUsername(), no, num, 1);

                    /*花500    存款100*    100 - 500  -400  欠款+400 存款为0*/
                    /*花100    存款500*   500 - 100  400   欠款不变 存款为400*/
                    //如果欠款大于零 也就是欠钱了，就先给填补账单  再把多的钱拿去预存金额
                    double newOwer;
                    double newPresent;
                    //判断是否需要改变存款 如果需要这进行更改
                    //这里改变了 欠款 和 预存

                    if (one.getCreditPresent() > 0) {

                        num = one.getCreditPresent() - num;
                        if (num < 0) {
                            newOwer = one.getCreditable() + (-num) * 1.1;
                            one.setCreditower(newOwer);
                            one.setCreditPresent(0);
                        } else {
                            one.setCreditPresent(num);
                        }

                    } else {
                        newOwer = one.getCreditower() + num;
                        one.setCreditower(newOwer);
                    }
                }
            }
        }
        Tool.setAccountVo(listAccountVo);

        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //还款
    public static void Refund(String no) {
        System.out.println("信息核对中*********");
        List<AccountVo> listAccountVo = Tool.getAccountVO();
        for (AccountVo one : listAccountVo) {
            //判断是否能进行消费
            if (one.getState() == 0) {
                System.out.println("抱歉,卡号为" + one.getCardNo() + "的信用卡被冻结，请及时联系用户工作人员");

            } else {

                if (one.getCardNo().equals(no)) {
                    System.out.print("本次还款的金额为(\\元):");
                    double num = sc.nextInt();
                    billNew(one.getUsername(), no, num, 2);

                    /* 欠500   存100*  100 - 500  -400*/
                    /*欠100    存500*   500 - 100  400*/
                    //如果欠款大于零 也就是欠钱了，就先给填补账单  再把多的钱拿去预存金额

                    //判断是否需要改变欠款 如果需要这进行更改
                    if (one.getCreditower() > 0) {
                        num = num - one.getCreditower();
                        if (num < 0) {
                            one.setCreditower(-num);
                            num = 0;
                        } else
                            one.setCreditower(0);

                    }
                    //改变预存金额  ***
                    num = num + one.getCreditPresent();
                    one.setCreditPresent(num);
                    //改变总信用额度  和 可用信用额    每次预存五百总信用额度增加250 可用增加200
                    if (num > 500) {
                        one.setCreditTotal(one.getCreditTotal() + 250);
                        one.setCreditable(one.getCreditable() + 200);
                    }
                    //改变可取现额度 =可用 /1.1
                    one.setCreditCash((one.getCreditable()) / (1.1));

                }
            }
        }

        Tool.setAccountVo(listAccountVo);
        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //取现
    public static void Cash(String no) {
        System.out.println("信息核对中*********");
        List<AccountVo> listAccountVo = Tool.getAccountVO();

        for (AccountVo one : listAccountVo) {
            //判断是否能进行消费
            if (one.getState() == 0) {
                System.out.println("抱歉,卡号为" + one.getCardNo() + "的信用卡被冻结，请及时联系用户工作人员");

            } else {
                if (one.getCardNo().equals(no)) {
                    System.out.print("本次取现的金额为(\\元):");
                    double num = sc.nextInt();
                    //判断是否能进行取现
                    if ((one.getCreditCash()) < num) {
                        System.out.println("抱歉 卡号为" + one.getCardNo() + "的信用卡可取现度只有  " + one.getCreditCash());
                    } else {
                        billNew(one.getUsername(), no, num, 3);

                        //*  额度足够直接取现    改变账单 (需要百分之十的手续费)和可取现额度
                        double newOwer = one.getCreditower() + num * 1.1;
                        one.setCreditower(newOwer);

                        double newableCash = one.getCreditCash() - num;
                        one.setCreditCash(newableCash);

                        //改变可用信用额度 = 可取现*1.1
                        double newablecred = one.getCreditable() * 1.1;
                        one.setCreditable(newablecred);
                    }
                }
            }
        }
        Tool.setAccountVo(listAccountVo);

        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //我的账单
    public static void MyBill(String no) {
        List<BillVo> listBillVo = Tool.getBillVO();
        if (listBillVo.size() == 0) {
            System.out.println("-----------------------------------------------");
            System.out.println("*---------------该用户无账单账单--------------*");
            System.out.println("-----------------------------------------------");
        }
        int flag = 0;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("账单号\t\t账号\t用户名\t\t金额\t\t类型");
        for (BillVo one : listBillVo) {
            if (one.getCardNo().equals(no)) {
                System.out.println(one.getBillNo() + "\t" + one.getCardNo() + "\t" + one.getUserName() + "\t\t" + one.getValue() + "\t\t" + one.getType());
                flag = 1;
            }
        }
        if (flag == 0) {
            System.out.println("-------------------------该用户不存在账单----------------------------");
        }
        System.out.println("---------------------------------------------------------------------");

        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //我的账户
    public static void MyAccount(String no) {
        List<AccountVo> listAccountVo = Tool.getAccountVO();
        int flag = 0;
        for (AccountVo one : listAccountVo) {

            if (one.getCardNo().equals(no)) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("卡号\t\t户主");
                System.out.println("" + one.getCardNo() + "\t\t" + one.getUsername());
                System.out.println("总信用额\t可用信用额\t可取现额\t欠款金额\t预存金额\t状态");
                String able = String.format("%.2f", one.getCreditable());  //把  可用信用  额转化为尾数为两位的double类型
                String cash = String.format("%.2f", one.getCreditCash());  //把  可取现额  转化为尾数为两位的double类型
                String ower = String.format("%.2f", one.getCreditower());  //把  欠款  转化为尾数为两位的double类型
                System.out.println("" + one.getCreditTotal() + "\t\t" + able + "\t\t" + cash + "\t\t" + ower + "\t\t" + one.getCreditPresent() + "\t\t\t" + one.getState());
                System.out.println("--------------------------------------------------------------------");
                flag =1;
                break;
            }

        }
        if (flag ==0){
            System.out.println("无法查询到你寻找的用户----------------");
        }
        System.out.print("\n按回车键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
