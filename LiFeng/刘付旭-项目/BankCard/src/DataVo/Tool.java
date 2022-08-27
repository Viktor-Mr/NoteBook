package DataVo;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tool implements Serializable {  //工具类

    public static List<AccountVo> getAccountVO() {           //对象输入流

        List<AccountVo> listAccount = new LinkedList<>();    //建立空链表
        InputStream fis = null;                //建立一个空的字节流
        ObjectInputStream ois = null;          //建立一个空的对象字节流

        try {
            fis = new FileInputStream("IdTxt\\AccountVo.txt");  //给字节流赋值
            ois = new ObjectInputStream(fis);                      //给对象流赋值
            listAccount = (List<AccountVo>) ois.readObject();    //如果不为空

        } catch (EOFException e) {
            return listAccount;     //如果为文本为空则返回一个空集合，对象流无法正确赋值  则放回一个空链表

        } catch (Exception e) {
            System.out.println("无法找到存储文件的路径");
            System.out.println("----  io流异常 -------");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                System.out.println("----- io流异常  ------");
            }
        }

        return listAccount;      //返回列表
    }


    public static void setAccountVo(List list) {  //对象输出流
        OutputStream fos = null;
        ObjectOutputStream os = null;

        try {
            fos = new FileOutputStream("IdTxt\\AccountVo.txt");
            os = new ObjectOutputStream(fos);
            os.writeObject(list); //把集合通过对象IO流 Write 到文件
            os.flush();
        } catch (IOException e) {
            System.out.println("---  io流异常    ---");
        }
    }


    public static List<BillVo> getBillVO() {  //对象输入流
        //这里就给它一个对象 避免文件为空无法赋值，导致返回空链表
        List<BillVo> listBill = new LinkedList<>();
        InputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("IdTxt\\BillVo.txt");
            ois = new ObjectInputStream(fis);
            //如果不为空
            listBill = (List<BillVo>) ois.readObject();
        } catch (EOFException e) {
            //如果为空则返回一个空集合
            return listBill;
        } catch (Exception e) {
            System.out.println("---  io流异常    ---");
            System.out.println("---  类型匹配    ---");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                System.out.println("---  io流异常    ---");
            }
        }
        //返回列表
        return listBill;
    }

    public static void setBillVo(List list) {  //对象输出流

        OutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = new FileOutputStream("IdTxt\\BillVo.txt");
            os = new ObjectOutputStream(fos);
            //把集合通过对象IO流 Write 到文件
            os.writeObject(list);
            os.flush();
        } catch (IOException e) {
            System.out.println("---  io流异常    ---");
        }
    }

}
