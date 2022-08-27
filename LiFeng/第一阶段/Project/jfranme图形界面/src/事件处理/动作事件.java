package 事件处理;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class 动作事件 {
    public static void main(String[] args) {
        new ActionTest();
    }
}

class ActionTest extends JFrame {
    JButton  jbapple = new JButton("苹果");
    JButton  jbsanxing = new JButton("三星");
    JButton  jbxiaomi = new JButton("小米");
    JButton  jbhuawei = new JButton("华为");
    public ActionTest(){
        this.setLayout(new FlowLayout());

        this.add(jbapple);
        this.add(jbsanxing);
        this.add(jbxiaomi);
        this.add(jbhuawei);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("苹果"))
                    JOptionPane.showMessageDialog(ActionTest.this,"苹果手机");
                else if(e.getActionCommand().equals("小米"))
                    JOptionPane.showMessageDialog(ActionTest.this,"小米手机");
                else if(e.getActionCommand().equals("华为"))
                    JOptionPane.showMessageDialog(ActionTest.this,"华为手机");
                else if(e.getActionCommand().equals("三星"))
                    JOptionPane.showMessageDialog(ActionTest.this,"三星手机");
            }
        };

        jbapple.addActionListener(actionListener);
        jbsanxing.addActionListener(actionListener);
        jbxiaomi.addActionListener(actionListener);
        jbhuawei.addActionListener(actionListener);

        this.setVisible(true);
        this.setBounds(100,100,700,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}