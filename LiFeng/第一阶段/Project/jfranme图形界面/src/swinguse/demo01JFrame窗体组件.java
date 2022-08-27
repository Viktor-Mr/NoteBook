package swinguse;

import javax.swing.*;

public class demo01JFrame窗体组件 {
    public static void main(String[] args) {
        JFrame jframe = new JFrame("第一个窗口");
        jframe.setSize(500,300);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
