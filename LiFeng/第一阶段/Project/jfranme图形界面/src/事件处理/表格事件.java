package 事件处理;

import javafx.scene.control.Tab;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class 表格事件 {
    public static void main(String[] args) {
    new Table();
    }
}
class Table extends JFrame {
    JFrame frame = new JFrame("表格");

    //表格模型
    DefaultTableModel tablemodel;
    //表格
    JTable table;

    //增加按钮
    JButton btnAdd;
    //删除按钮
    JButton btnDel;
    //修改按钮
    JButton btnUpdate;

    //输入学号姓名的文本框
    JTextField number_TextField,name_TextField;

    public Table(){
        //制作 表格
        String[]  columuID = {"学号","名字"};
        String[][] table_student = {{"1","李明"},{"2","小王"},{"3","张三"}};

        //表格模式初始化                   表格内容  表格头
        tablemodel = new DefaultTableModel(table_student,columuID);
        table = new JTable(tablemodel);      //把表格模型加入表格
        JScrollPane sc = new JScrollPane(table);  //滚动界面
        frame.add(sc,BorderLayout.CENTER);      //边界布局 居中

        //制作按钮中间容器
        JPanel panel = new JPanel();
         number_TextField = new JTextField("",5);
         name_TextField = new JTextField("",5);
        panel.add(new JLabel("学号"));
        panel.add(number_TextField);
        panel.add(new JLabel("姓名"));
        panel.add(name_TextField);
        btnAdd = new JButton("增加");
        btnDel = new JButton("删除");
        btnUpdate = new JButton("修改");
        panel.add(btnAdd);
        panel.add(btnDel);
        panel.add(btnUpdate);
        frame.add(panel,BorderLayout.SOUTH);

        //组件的监听事件
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("增加")){
                   //获取文本款的数据并保存在数组
                    String newRow[] = { number_TextField.getText(),name_TextField.getText()};
                    //将新的一行加入表格
                    tablemodel.addRow(newRow);
                }
                else if (e.getActionCommand().equals("删除")){
                    int row = table.getSelectedRow(); //获取选中的行号
                    if (row!=-1){
                        tablemodel.removeRow(row);//删除这一行
                    }
                    else{
                        JOptionPane.showMessageDialog(Table.this,"请从表格选取一行再删除");
                    }
                }else if (e.getActionCommand().equals("修改")){
                    int row = table.getSelectedRow(); //获取行数
                    if (row!=-1){
                        tablemodel.setValueAt(number_TextField.getText(),row,0);
                        tablemodel.setValueAt(name_TextField.getText(),row,1);
                    } else{
                        JOptionPane.showMessageDialog(Table.this,"请从表格选取一行再修改");
                    }
                }

            }
        };

        //表格监听器
        tablemodel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
              //获取事件类型
                int type = e.getType();
                //获取触发事件的行索引
                int row = e.getFirstRow();
                //增加或删除行的情况判断
                if (type == TableModelEvent.INSERT){
                    System.out.println("表格添加了第" + (row+1) + "行");
                }else if(type == TableModelEvent.DELETE){
                    System.out.println("表格删除了第" + (row+1) + "行");
                }
                else if(type == TableModelEvent.UPDATE){
                    System.out.println("表格修改了第" + (row+1) + "行");
                }
            }
        });

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {  //鼠标单击了列表的其中一行
                int row = table.getSelectedRow(); //获取行数
                if (row!=-1){
                    tablemodel.setValueAt(number_TextField.getText(),row,0);
                    tablemodel.setValueAt(name_TextField.getText(),row,1);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        btnAdd.addActionListener(actionListener);
        btnDel.addActionListener(actionListener);
        btnUpdate.addActionListener(actionListener);

        frame.setVisible(true);
        frame.setSize(600,200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




}
