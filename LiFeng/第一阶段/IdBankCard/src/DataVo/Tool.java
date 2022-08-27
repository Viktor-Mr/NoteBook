package DataVo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tool implements Serializable {  //工具类

    public static List<AccountVo> getAccountVO() {  //对象输入流
        List<AccountVo> listAccount = null;
        InputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("IdTxt\\AccountVo.txt");
            try {
                ois = new ObjectInputStream(fis);
            } catch (EOFException e) {
                return listAccount;    //如果为空则返回一个空集合
            }

            while (true) {
                try {
                    listAccount = (List) ois.readObject();    //如果不为空
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //返回列表
        return listAccount;
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
            e.printStackTrace();
        }
    }


    public static List<BillVo> getBillVO() {  //对象输入流
        List<BillVo> listBill = null;
        InputStream fis = null;
        ObjectInputStream ois = null;


        try {
            fis = new FileInputStream("IdTxt\\BillVo.txt");
            try {
                ois = new ObjectInputStream(fis);
            } catch (IOException e) {
                return listBill;  //如果为空则返回一个空集合
            }

            while (true) {
                try {
                    listBill = (List) ois.readObject();   //如果不为空
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            os.writeObject(list); //把集合通过对象IO流 Write 到文件
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
