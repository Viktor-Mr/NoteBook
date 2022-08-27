package swinguse;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class demo02部分组件 {
    public static void main(String[] args) {
       // JlabelDemo demo02 = new JlabelDemo();  //标签组件

      //  JTextFieldDemo jTextFieldDemo = new JTextFieldDemo();  //文本组件

      // JPanelDemo jPanelDemo = new JPanelDemo(); //中间组件

        JTextAreaDemo jTextAreaDemo = new JTextAreaDemo(); //文本域

    }
}

/**
 *  JLabel 标签组件
 */

class JlabelDemo extends JFrame{
    public JlabelDemo(){

        //窗口组件
        this.setTitle("标签");
        this.setVisible(true);
        this.setSize(350,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //标签组件
        JLabel jLabel = new JLabel("TEST");
        jLabel.setFont(new Font("黑体",Font.BOLD,35));
        jLabel.setBackground(Color.BLUE);
        this.add(jLabel);
    }
}
/**
 * JTextField 文本组件
 */
class JTextFieldDemo extends  JFrame {
    public JTextFieldDemo(){
        //文本组件
        JTextField jTextField = new JTextField();
        jTextField.setText("jiji是我儿，我是jiji爹");
        this.add(jTextField);

        //窗口组件
        this.setTitle("标签");
        this.setVisible(true);
        this.setSize(350,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}

/**
 * JPanel 中间容器
 */
class JPanelDemo extends  JFrame {
    public JPanelDemo(){
//        //文本域
//        JTextArea textArea = new JTextArea(6,10);


        //中间组件
        JPanel jpl = new JPanel();

        //文本组件
        JTextField jTextField = new JTextField();
        jTextField.setText("jiji是我儿，我是jiji爹");

        //标签组件
        JLabel jLabel = new JLabel("TEST");
        jLabel.setFont(new Font("黑体",Font.BOLD,35));
        jLabel.setBackground(Color.BLUE);

        //把文本组件 标签组件加到中间组件，再把中间组件加入到窗口
        jpl.add(jTextField);
        jpl.add(jLabel);
        this.add(jpl);

        //窗口组件
        this.setTitle("标签");
        this.setVisible(true);
        this.setSize(350,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}

class JTextAreaDemo extends  JFrame {
    public JTextAreaDemo(){
        //文本域
        JTextArea textArea = new JTextArea(6,10);
        textArea.setText("自我介绍");

        JScrollPane jsp = new JScrollPane(textArea);

        //中间组件
        JPanel jpl = new JPanel();

        //文本组件
        JTextField jTextField = new JTextField();
        jTextField.setText("jiji是我儿，我是jiji爹");

        //标签组件
        JLabel jLabel = new JLabel("TEST");
        jLabel.setFont(new Font("黑体",Font.BOLD,35));
        jLabel.setBackground(Color.BLUE);

        //把文本组件 标签组件加到中间组件，再把中间组件加入到窗口
        jpl.add(jTextField);
        jpl.add(jsp);
        jpl.add(jLabel);
        this.add(jpl);

        //窗口组件
        this.setTitle("标签");
        this.setVisible(true);
        this.setSize(350,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
