package swinguse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class messagesboark {
    public static void main(String[] args) {
        new BoarkDemo();
    }
}

class BoarkDemo  extends JFrame {

    //自定义组件
    JTextArea areaMsg;
    JTextField txtUser;
    JTextField txtMsg;
    JButton  btnSend;

    public BoarkDemo(){

        areaMsg = new JTextArea(10,30);   //创建文本域以显示留言内容
        areaMsg.setEnabled(false);                         //让文本域可以进行编辑
        areaMsg.setFont(new Font("黑体",Font.BOLD,15));  //设置文本域的字体样式

        JScrollPane msgPane = new JScrollPane(areaMsg);        //创建滚动条，把文本域放进滚动条
        this.add(msgPane, BorderLayout.CENTER);                 //把滚动条添加到添加到主窗口

        JPanel  sendPanel = new JPanel();          //创建中间容器，放置发送留言的相关组件

        txtUser = new JTextField(6);    //创建文本框  用于输入用户名 列数为6
         txtMsg = new JTextField(20);    //创建文本框 用于输入留言信息，列数为20
         btnSend  = new JButton("发送");     //创建按钮 ，发送信息

        JLabel lbUser =  new JLabel("用户名");   //创建文本标签
        JLabel lbMsg =  new JLabel("留言");

        sendPanel.add(lbUser);      //添加 -----  到中间域
        sendPanel.add(txtUser);
        sendPanel.add(lbMsg);
        sendPanel.add(txtMsg);
        sendPanel.add(btnSend);
        this.add(sendPanel,BorderLayout.SOUTH);  //中间域添加到主窗体，位置在南方


        this.setTitle("留言板");
        this.setSize(500,200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnSend.addActionListener(new AbstractAction() {  //绑定发送按钮单击事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String  user = txtUser.getText();
                String  msg = txtMsg.getText();
                 msg =   user+ " : "+ msg + "\n";
                 areaMsg.append(msg);
                 txtMsg.setText("");
                 txtUser.setText("");
            }
        });


    }
}
