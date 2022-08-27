package firsthomework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;



public class task03 {
    public static void main(String[] args) {
       Demo demo = new Demo();
    }
}
class Demo  extends JFrame {
    JTextField  txtuser;
    JPasswordField  txtpws;
    JTextField  txtbri;
    JTextField  txtph;
    JTextField  txtsex;


    public Demo(){
        this.setLayout(new GridLayout(7,1));
        JLabel lbsign = new JLabel("用户注册",JLabel.CENTER);
        this.add(lbsign);

        JLabel lbuser =  new JLabel("用户名:");
        txtuser = new JTextField(13);
        JPanel pnluser =new JPanel();
        pnluser.add(lbuser);
        pnluser.add(txtuser);
        this.add(pnluser);

        JLabel lbpws =  new JLabel("密  码:");
        txtpws = new  JPasswordField(13);
        txtpws.setEchoChar('*');
        JPanel pnlpws =new JPanel();
        pnlpws.add(lbpws);
        pnlpws.add(txtpws);
        this.add(pnlpws);

        JLabel lbbri =  new JLabel("出生日期:");
         txtbri = new JTextField(13);
         txtbri.setText("yyyy-MM-dd");
        JPanel pnlbri =new JPanel();
        pnlbri.add(lbbri);
        pnlbri.add(txtbri);
        this.add(pnlbri);

        JLabel lbph =  new JLabel("电话号码:");
         txtph = new JTextField(14);
        JPanel pnlph =new JPanel();
        pnlph.add(lbph);
        pnlph.add(txtph);
        this.add(pnlph);


        JLabel lbsex =  new JLabel("性  别:");
       txtsex = new JTextField(14);
        JPanel pnlsex =new JPanel();
        pnlsex.add(lbsex);
        pnlsex.add(txtsex);
        this.add(pnlsex);

        JButton btnsign = new JButton("注  册");
        this.add(btnsign);

        this.setVisible(true);
        this.setBounds(100,100,700,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnsign.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
                Verify verify = new Verify(txtsex.getText(),txtbri.getText(),txtph.getText());  //验证
                String an =verify.ver();
                 JOptionPane.showMessageDialog(Demo.this,an,"注册",JOptionPane.WARNING_MESSAGE);

            }
        });

    }
}

class Verify{
    private  String sex;
    private String bir;
    private String ph;

    public String getSex() {
        return sex; }

    public void setSex(String sex) {
        this.sex = sex; }

    public String getBir() {
        return bir; }

    public void setBir(String bir) {
        this.bir = bir; }

    public String getPh() {
        return ph; }

    public void setPh(String ph) {
        this.ph = ph; }

    public Verify(String sex, String bir, String ph) {
        this.sex = sex;
        this.bir = bir;
        this.ph = ph;
    }


    public String ver(){
        String error = "";
        int num = 0;
        if(sex.trim().equals("男") || sex.trim().equals("女"))
            num++;
        else
            error += "性别输入错误\n";

        String regex = "(\\d{11})|(\\d{8})";
        if (ph.matches(regex))
            num++;
        else
            error += "电话号码错误\n";

        regex = "(\\d{4}-(0|1)\\d-((0|1|2)\\d)|(3(0|1)))";
        if (bir.matches(regex))
            num++;
        else
            error += "出生格式错误";

        if(num==3)
             return  "注册成功";
        else
            return error;
    }

}
