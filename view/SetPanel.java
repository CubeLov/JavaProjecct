package view;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class SetPanel extends JPanel{
    private final int WIDTH;
    private final int HEIGTH;
    private JRadioButton difficulty1;
    private JRadioButton difficulty2;
    private JRadioButton difficulty3;
    private ButtonGroup difficultyGroup;
    private JButton nextButton;

    public SetPanel(int width,int height){
        this.WIDTH=width;
        this.HEIGTH=height;
        setPreferredSize(new Dimension(width,height));
        setBounds(0,0,width,height);
        setLayout(null);
        addDifficultyGroup();
        addNextButton();
    }
    private void addDifficultyGroup(){
        JLabel label=new JLabel("Difficulty");
        label.setFont((new Font("Rockwell", Font.BOLD, 50)));
        label.setBounds(0,100,400,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        difficulty1=new JRadioButton(" EASY");
        difficulty2=new JRadioButton(" MEDIUM");
        difficulty3=new JRadioButton(" HARD");
        difficultyGroup=new ButtonGroup();

        difficulty1.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty1.setFocusPainted(false);
        difficulty1.setBounds(200,350,200,80);
        add(difficulty1);

        difficulty2.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty2.setFocusPainted(false);
        difficulty2.setBounds(450,350,200,80);
        add(difficulty2);

        difficulty3.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty3.setFocusPainted(false);
        difficulty3.setBounds(700,350,200,80);
        add(difficulty3);

        difficultyGroup.add(difficulty1);
        difficultyGroup.add(difficulty2);
        difficultyGroup.add(difficulty3);
    }


    private void addNextButton(){
        nextButton=new JButton("Next");
        nextButton.setFont((new Font("Rockwell", Font.BOLD, 25)));
        nextButton.setFocusPainted(false);
        nextButton.setBounds(WIDTH-280,HEIGTH-200,200,80);
        add(nextButton);
    }
    /*
    返回ButtonGroup中选择按钮的文本
     */
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    public ButtonGroup getDifficultyGroup() {
        return difficultyGroup;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public static void main(String[] args) {
        SetPanel s=new SetPanel(1100,810);
        JFrame frame=new JFrame("Test");
        frame.setSize(1100,810);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //frame.setContentPane(s);
        frame.setVisible(true);
//        while(true) {
//            System.out.println(s.getSelectedButtonText(s.getBoardSizeGroup()));
//        }
    }
}
