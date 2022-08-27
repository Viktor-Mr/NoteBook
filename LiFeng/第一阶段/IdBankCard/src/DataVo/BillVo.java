package DataVo;

import java.io.Serializable;

/**
 *  账单信息
 *
 * 账单号 billNo  账单号生成规律：P+日期+时+分+  秒如 P20101012112558
 * 用户名 userName
 * 账号 cardNo
 * 金额 value
 * 类型 type Int 1:为消费 2:还款 3:取现

 */
public class BillVo implements Serializable {
    private String  billNo;   // 账单号生成规律：P+日期+时+分+秒如 P20101012112558
    private String  userName;
    private String  cardNo;
    private double  value;
    private int type;   // 1 消费 2 还款 3 取现

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BillVo() {
    }

    public BillVo(String billNo, String userName, String cardNo, double value, int type) {
        this.billNo = billNo;
        this.userName = userName;
        this.cardNo = cardNo;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "BillVo{" +
                "billNo='" + billNo + '\'' +
                ", userName='" + userName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}

