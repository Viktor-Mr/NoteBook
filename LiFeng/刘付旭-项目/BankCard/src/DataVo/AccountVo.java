package DataVo;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**   settlementDate 每月结算日期
* creditTotal;        总信用额
 * creditTable;        可用信用额
 * creditower;        可取现额度
 *creditPresent;      预存金额
 * state;
 *
 */
public class AccountVo implements Serializable {//序列化接口
    private String cardNo;  //卡号
    private String passWord;  //账号密码
    private String username;  //用户名
    private Date settlementDate ;  //每月结算日期
    private double  creditTotal = 10000;  //总信用额度   10000
    private double  creditTable  = 0;  //可用信用额度
    private double    creditCash =0; //可取现额度
    private double  creditower  = 0;  //欠款
    private double  creditPresent = 0;  //预存金额

    private  int  state  = 1;          // 状态  1、开通 0、冻结

    //三个参数的构造方法
    public AccountVo( String username ,String cardNo,String passWord) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

        this.cardNo = cardNo;
        this.passWord = passWord;
        this.username = username;

        this.creditTotal = (double)5000;
        this.creditTable = (double)5000;
        this.creditCash  = (5000/1.1);
        this.creditPresent = 0.0;
        this.creditower = 0.0;
        this.creditPresent =0;
        this.state = 1;

    }

    public AccountVo(String cardNo, String passWord, String username, Date settlementDate, double creditTotal, double creditTable, double creditCash, double creditower, double creditPresent, int state) {
        this.cardNo = cardNo;
        this.passWord = passWord;
        this.username = username;
        this.settlementDate = settlementDate;
        this.creditTotal = creditTotal;
        this.creditTable = creditTable;
        this.creditCash = creditCash;
        this.creditower = creditower;
        this.creditPresent = creditPresent;
        this.state = state;
    }

    public double getCreditTable() {
        return creditTable;
    }

    public void setCreditTable(double creditTable) {
        this.creditTable = creditTable;
    }

    public double getCreditCash() {
        return creditCash;
    }

    public void setCreditCash(double creditCash) {
        this.creditCash = creditCash;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

    public double getCreditable() {
        return creditTable;
    }

    public void setCreditable(double creditable) {
        this.creditTable = creditable;
    }

    public double getCreditower() {
        return creditower;
    }

    public void setCreditower(double creditower) {
        this.creditower = creditower;
    }

    public double getCreditPresent() {
        return creditPresent;
    }

    public void setCreditPresent(double creditPresent) {
        this.creditPresent = creditPresent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AccountVo{" +
                "cardNo='" + cardNo + '\'' +
                ", passWord='" + passWord + '\'' +
                ", username='" + username + '\'' +
                ", settlementDate=" + settlementDate +
                ", creditTotal=" + creditTotal +
                ", creditable=" + creditTable +
                ", creditower=" + creditower +
                ", creditPresent=" + creditPresent +
                ", state=" + state +
                '}';
    }
}
