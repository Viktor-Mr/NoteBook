package swinguse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.FloatBuffer;

public class demo05按钮第三发触发 {
    public static void main(String[] args) {
        new Dome();
    }
}

class  Dome extends JFrame {
    JRadioButton jman = new JRadioButton("男",true);
    JRadioButton jwoman = new JRadioButton("女");
    JButton jb  = new JButton("选择");


    ButtonGroup group = new ButtonGroup();
    public Dome(){
        this.setLayout(new FlowLayout()); //流式布局

        JLabel jp = new JLabel("请选择性别: ");
       this.add(jp);




//       group.add(jman);   //把男女加入到 同一个按钮组
//       group.add(jwoman);

       this.add(jman);  //把 男 女 加入到图形中
       this.add(jwoman);
       this.add(jb);  //选择按钮


        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jman.isSelected()){
                    JOptionPane.showMessageDialog(Dome.this,"男生","性别选择",JOptionPane.INFORMATION_MESSAGE);
                }
                if(jwoman.isSelected()){
                    JOptionPane.showMessageDialog(Dome.this,"女生","性别选择",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        this.setVisible(true);
        this.setSize(300,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}