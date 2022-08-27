package swinguse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;

public class demo04JRadioButton单选框 {
    public static void main(String[] args) {
        JRadioButtonDemo jRadioButtonDemo = new JRadioButtonDemo();
    }
}
class  JRadioButtonDemo extends  JFrame{
        JRadioButton rdtn1 = new JRadioButton("加粗");  //创建单选按钮
        JRadioButton rdtn2 = new JRadioButton("斜体");
        JRadioButton rdtn3 = new JRadioButton("隶书");
        JRadioButton rdtn4 = new JRadioButton("红色");
        JLabel lb = new JLabel("测试-测试",JLabel.CENTER);   //创建标签，居中显示

        JRadioButtonDemo(){
            lb.setFont(new Font("宋体",Font.PLAIN,20));
            this.add(lb,BorderLayout.CENTER); //奖标签加到主窗口体

            JPanel panel = new JPanel();//创建面板
            ButtonGroup group = new ButtonGroup();//创建按钮组
            group.add(rdtn1);    //将单选按钮添加到按钮组
            group.add(rdtn2);
            group.add(rdtn3);
            group.add(rdtn4);

            panel.add(rdtn1);   //将按钮组添加中间容器（面板）
            panel.add(rdtn2);
            panel.add(rdtn3);
            panel.add(rdtn4);
            this.add(panel,BorderLayout.SOUTH);  //将中间容器添加到主窗口体南部

            this.setVisible(true);
            this.setSize(250,250);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ActionListener  listener= new AbstractAction() {  //创建单击事件监听器
                @Override
                public void actionPerformed(ActionEvent e) {
                    int fontstyle =0;
                    String fontName = "宋体";
                    if (rdtn1.isSelected())
                        fontstyle = Font.BOLD;
                    if (rdtn2.isSelected())
                        fontstyle = Font.ITALIC;
                    if (rdtn3.isSelected())
                        fontName = "隶书";
                    if (rdtn4.isSelected())
                        lb.setForeground(Color.RED);
                    else
                        lb.setForeground(null);
                    lb.setFont(new Font(fontName,fontstyle,20));
                }
            };

            rdtn1.addActionListener(listener);  //将单选按钮绑定单击事件
            rdtn2.addActionListener(listener);
            rdtn3.addActionListener(listener);
            rdtn4.addActionListener(listener);
        }

}
