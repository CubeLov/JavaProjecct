package view;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private  String id;
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel label;
    private JTextField inputName;
    private JButton confirmButton;
    public RegisterPanel(int width, int height){
        this.WIDTH=width;
        this.HEIGTH=height;
        setPreferredSize(new Dimension(width,height));
        setBounds(0,0,width,height);
        setLayout(null);
        addLabel();
        addConfirmButton();
        addInputName();
    }
    private void addInputName(){
        inputName=new JTextField(5);
        inputName.setFont((new Font("Rockwell", Font.PLAIN, 40)));
        inputName.setBounds(WIDTH/2-200,HEIGTH/2-100,400,100);
        add(inputName);
    }
    private void addConfirmButton(){
        confirmButton=new JButton("Confirm");
        confirmButton.setFont((new Font("Rockwell", Font.BOLD, 25)));
        confirmButton.setFocusPainted(false);
        confirmButton.setBounds(WIDTH-280,HEIGTH-200,200,80);
        add(confirmButton);
    }
    private void addLabel(){
        label=new JLabel("Pleas Input Your Name:");
        label.setFont((new Font("Rockwell", Font.BOLD, 40)));
        label.setBounds(50,100,500,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }

    public String getId() {
        return id;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getText(){
        return inputName.getText();
    }
    public static void main(String[] args) {
        RegisterPanel s=new RegisterPanel(1100,810);
        JFrame frame=new JFrame("Test");
        frame.setSize(1100,810);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //frame.setContentPane(s);
        frame.setVisible(true);
    }
}
