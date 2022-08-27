package swinguse;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class demo07Imagelcon图片按钮{
    public static void main(String[] args) {
        new ImageelconDome();
    }
}
class ImageelconDome extends JFrame {
    public ImageelconDome(){
        this.setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon("D:\\砺锋\\第一阶段\\Project\\jfranme图形界面\\images\\glc.jpg"); //创建图片
        icon.setImage(icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));

        JButton jButton = new JButton("查找",icon);
      //  jButton.setBorderPainted(false);    //消除边框
       // jButton.setContentAreaFilled(false);  //消除填充
        jButton.setHorizontalTextPosition(SwingConstants.CENTER);  //水平居中
        jButton.setVerticalTextPosition(SwingConstants.BOTTOM);    //垂直靠下
        this.add(jButton);

//
//        jButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(true){
//                    JOptionPane.showMessageDialog(ImageelconDome.this, "登录成功", "登录", JOptionPane.INFORMATION_MESSAGE);
//
//                    new Interface();
//                    ImageelconDome.this.dispose();
//                }
//            }
//        });

        ActionListener listener  =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       //         if(e.getActionCommand().equals("查找")){
                  if(jButton.isSelected()){
                    new Interface();
                }
            }
        };
        jButton.addActionListener(listener);

        this.add(jButton);
        this.setVisible(true);
        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
