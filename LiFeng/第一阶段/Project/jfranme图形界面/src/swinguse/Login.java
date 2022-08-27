package swinguse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login {
    public static void main(String[] args) {
      new Interface();
    }
}
class Interface  extends JFrame{
    public Interface(){

        //将窗口设置为表格布局4行1列，每添加一个组件自动放入一个单元格
        this.setLayout(new GridLayout(4,1));
        JLabel jlbelLogin  = new JLabel("登录",JLabel.CENTER);//创建登录标签，并且居中
        //jlbelLogin.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlbelLogin); //将登录标签添加到主窗口

        JLabel lblUser = new JLabel("用户名:"); //创建用户名标签
        JTextField txtUser = new JTextField(10); //为用户名标签创建文本框
        JPanel pnlName = new JPanel();  //创建中间容器
        pnlName.add(lblUser);    //向中间容器加入标签
        pnlName.add(txtUser);   //向中间容器加入文本框
        this.add(pnlName);       //把中间容器加主窗口

        JLabel lblPwd = new JLabel("密  码:");    //创建密码标签
        JPasswordField txtPwd = new JPasswordField(10); //为密码标签创建文本框（特殊文本框）
        txtPwd.setEchoChar('*');                                     //设置密码款的掩码
        JPanel pnlPwd = new JPanel();               //创建中间容器
        pnlPwd.add(lblPwd);                         //向中间容器加入密码标签
        pnlPwd.add(txtPwd);                         //向中间容器加入文本框
        this.add(pnlPwd);                             // 把中间容器加入主窗口


        JButton btnLogin  = new JButton("登录");     //创建登录按钮
        JButton btnRegister = new JButton("注册");   //创建注册按钮
        JPanel  pnlBtn = new JPanel();                     //创建中间容器
        pnlBtn.add(btnLogin);                              //向中间容器加入登录按钮
        pnlBtn.add(btnRegister);                            //向中间容器加入注册按钮
        this.add(pnlBtn);                                  //把中间容器加入主窗口


        this.setTitle("系统");
        this.setBackground(Color.GRAY);
        this.setBounds(100,100,300,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        btnLogin.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtUser.getText().equals("admin") && txtPwd.getText().equals("123"))
                {       JOptionPane.showMessageDialog(Interface.this, "登录成功", "登录", JOptionPane.INFORMATION_MESSAGE);

                new BoarkDemo();           //打开新窗口
                Interface.this.dispose();  //关闭之前的窗口
            }
                else
                    JOptionPane.showMessageDialog(Interface.this,"登录失败","登录",JOptionPane.ERROR_MESSAGE);
            }
        });

    }

}
