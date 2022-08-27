package swinguse;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class demo03JCheckBox多选框 {
    public static void main(String[] args) {
        JCheckBoxDemo jch = new JCheckBoxDemo();
    }
}

class JCheckBoxDemo extends JFrame {
    JCheckBox c1 = new JCheckBox("音乐");  //创建复选框
    JCheckBox c2 = new JCheckBox("游戏");
    JCheckBox c3 = new JCheckBox("电影");
    JTextArea jt = new JTextArea(10, 10);  //创建一个文本域
    String content = "";  //全局变量

    public JCheckBoxDemo() {

        JScrollPane jp = new JScrollPane(jt);  //添加到滚动面板
        this.add(jp, BorderLayout.CENTER);    //将滚动面板添加到主题窗口，位置为中央

        JPanel jp1 = new JPanel();  //创建一个面板
        jp1.add(c1);                 //将c1 c2 c3 分别添加到面板
        jp1.add(c2);
        jp1.add(c3);
        this.add(jp1, BorderLayout.SOUTH);  //将面板添加到主窗体下部

        this.setSize(300, 150);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (c1.isSelected()) {
                    if (!content.contains("音乐\n"))
                    { content += "音乐\n"; }
                } else {
                    content = content.replaceAll("音乐\n", "");
                }

                if (c2.isSelected()) {
                    if (!content.contains("游戏\n"))
                    { content += "游戏\n"; }
                } else {
                    content = content.replaceAll("游戏\n", "");
                }

                if (c3.isSelected()) {
                    if (!content.contains("电影\n"))
                    { content += "电影\n"; }
                } else {
                    content = content.replaceAll("电影\n", "");
                }

                jt.setText(content);    //重新给文本域赋值
            }
        }; //创建单击处理事件

        c1.addActionListener(listener);  //为c1复选框绑定事件
        c2.addActionListener(listener);
        c3.addActionListener(listener);
    }

}
