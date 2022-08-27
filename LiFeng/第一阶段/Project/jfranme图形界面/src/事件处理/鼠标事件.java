package 事件处理;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class 鼠标事件 {
    public static void main(String[] args) {
        new MouseDemo();
    }
}
class MouseDemo extends JFrame {
    public MouseDemo(){
        this.setLayout(new FlowLayout());
        JButton button = new JButton("按钮"); //创建按钮，放在窗体
        this.add(button);

        this.setSize(350,250);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //为按钮注册监听器
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {  //鼠标单击事件
                System.out.println("你单击了按钮");
            }

            @Override
            public void mousePressed(MouseEvent e) {  //鼠标按下事件
                System.out.println("鼠标按下事件");
            }

            @Override
            public void mouseReleased(MouseEvent e) {   //鼠标释放事件
                System.out.println("鼠标释放事件");
            }

            @Override
            public void mouseEntered(MouseEvent e) {//鼠标进入事件
                System.out.println("鼠标进入事件");
            }

            @Override
            public void mouseExited(MouseEvent e) {//鼠标移出事件
                System.out.println("鼠标移出事件");
            }
        });

    }
}
