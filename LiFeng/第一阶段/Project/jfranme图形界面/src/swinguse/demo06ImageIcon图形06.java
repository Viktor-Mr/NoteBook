package swinguse;

import javax.swing.*;
import java.awt.*;

public class demo06ImageIcon图形06 {
    public static void main(String[] args) {
        new Imageelcon();
    }
}
class Imageelcon extends JFrame {

    public Imageelcon(){
        ImageIcon icon = new ImageIcon("11.jpg"); //创建图片
        icon.setImage(icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
        JLabel lbl = new JLabel(icon);




        this.add(lbl);
        this.setVisible(true);
        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
       }
}
