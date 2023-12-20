package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class AddWindows extends JFrame implements MenuListener,ActionListener{
    JMenuBar bar;
    JMenu fileMenu;
    JMenu gradeOne,gradeTwo,gradeThree;//关卡级别

    public ChessGameFrame chessGameFrame;
    public AddWindows(){
        setSize(200, 400);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        bar=new JMenuBar();
        fileMenu=new JMenu("Match-3 Games Level");
        gradeOne=new JMenu("关卡1");
        gradeTwo=new JMenu("关卡2");
        gradeThree=new JMenu("关卡3");
        fileMenu.add(gradeOne);
        fileMenu.add(gradeTwo);
        fileMenu.add(gradeThree);
        bar.add(fileMenu);
        setJMenuBar(bar);
        gradeOne.addMenuListener(this);
        gradeTwo.addMenuListener(this);
        gradeThree.addMenuListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
    }
    public void menuSelected(MenuEvent e){
        if(e.getSource()==gradeOne){
            ChessGameFrame a=new ChessGameFrame(1100,810);
            a.setSteplimits(20);
            a.setScore(50);
        }
        else if(e.getSource()==gradeTwo){
            ChessGameFrame a=new ChessGameFrame(1100,810);
            a.setSteplimits(15);
            a.setScore(100);
        }
        else if(e.getSource()==gradeThree){
            ChessGameFrame a=new ChessGameFrame(1100,810);
            a.setSteplimits(10);
            a.setScore(120);
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}