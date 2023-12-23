package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class StartPanel extends JPanel {
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel label;
    private JButton manualButton;
    private JButton loadButton;

    private JButton onlineButton;
    public StartPanel(int width, int height){
        this.WIDTH=width;
        this.HEIGTH=height;
        setPreferredSize(new Dimension(width,height));
        setBounds(0,0,width,height);
        setLayout(null);
        addLabel();
        addManualButton();
        addLoadButton();
        addOnlineBUtton();
    }
    private void addLabel(){
        label=new JLabel("Match-3 Games");
        label.setFont((new Font("Rockwell", Font.BOLD, 80)));
        label.setBounds(230,HEIGTH/2-250,650,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
    private void addManualButton(){
        manualButton=new JButton("Manual Mode");
        manualButton.setFont((new Font("Rockwell", Font.PLAIN, 25)));
        manualButton.setFocusPainted(false);
        manualButton.setBounds(WIDTH/2-100,HEIGTH/2-80,200,80);
        add(manualButton);
    }
    private void addLoadButton(){
        loadButton=new JButton("Load");
        loadButton.setFont((new Font("Rockwell", Font.PLAIN, 25)));
        loadButton.setFocusPainted(false);
        loadButton.setBounds(WIDTH/2-100,HEIGTH/2+20,200,80);
        add(loadButton);

    }
    private void addOnlineBUtton(){
        onlineButton=new JButton("Online Mode");
        onlineButton.setFont((new Font("Rockwell", Font.PLAIN, 25)));
        onlineButton.setFocusPainted(false);
        onlineButton.setBounds(WIDTH/2-100,HEIGTH/2+120,200,80);
        add(onlineButton);
    }

    public JButton getManualButton() {
        return manualButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getOnlineButton() {
        return onlineButton;
    }

    public static void main(String[] args) {
        StartPanel s=new StartPanel(1100,810);
        JFrame frame=new JFrame("Test");
        frame.setSize(1100,810);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //frame.setContentPane(s);
        frame.setVisible(true);
    }
}
