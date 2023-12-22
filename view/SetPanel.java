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
    private JRadioButton boardSize1;
    private JRadioButton boardSize2;
    private JRadioButton boardSize3;
    private JTextField customizedBoardSize;
    private ButtonGroup boardSizeGroup;
    private JButton nextButton;

    public SetPanel(int width,int height){
        this.WIDTH=width;
        this.HEIGTH=height;
        setPreferredSize(new Dimension(width,height));
        setBounds(0,0,width,height);
        setLayout(null);
        addDifficultyGroup();
        addBoardSizeGroup();
        addNextButton();
    }
    private void addDifficultyGroup(){
        JLabel label=new JLabel("Difficulty");
        label.setFont((new Font("Rockwell", Font.BOLD, 40)));
        label.setBounds(50,20,200,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        difficulty1=new JRadioButton(" EASY");
        difficulty2=new JRadioButton(" MEDIUM");
        difficulty3=new JRadioButton(" HARD");
        difficultyGroup=new ButtonGroup();

        difficulty1.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty1.setFocusPainted(false);
        difficulty1.setBounds(200,150,200,80);
        add(difficulty1);

        difficulty2.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty2.setFocusPainted(false);
        difficulty2.setBounds(450,150,200,80);
        add(difficulty2);

        difficulty3.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        difficulty3.setFocusPainted(false);
        difficulty3.setBounds(700,150,200,80);
        add(difficulty3);

        difficultyGroup.add(difficulty1);
        difficultyGroup.add(difficulty2);
        difficultyGroup.add(difficulty3);
    }
    private void addBoardSizeGroup(){
        JLabel label=new JLabel("Board Size");
        label.setFont((new Font("Rockwell", Font.BOLD, 40)));
        label.setBounds(35,300,250,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        boardSize1=new JRadioButton(" 6 * 6");
        boardSize2=new JRadioButton(" 8 * 8");
        boardSize3=new JRadioButton(" Other");
        boardSizeGroup=new ButtonGroup();

        boardSize1.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        boardSize1.setFocusPainted(false);
        boardSize1.setBounds(200,420,200,80);
        add(boardSize1);

        boardSize2.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        boardSize2.setFocusPainted(false);
        boardSize2.setBounds(450,420,200,80);
        add(boardSize2);

        boardSize3.setFont((new Font("Rockwell", Font.PLAIN, 30)));
        boardSize3.setFocusPainted(false);
        boardSize3.setBounds(700,420,120,80);
        add(boardSize3);

        customizedBoardSize=new JTextField(5);
        customizedBoardSize.setFont((new Font("Rockwell", Font.PLAIN, 20)));
        customizedBoardSize.setBounds(820,445,70,40);
        add(customizedBoardSize);

        boardSizeGroup.add(boardSize1);
        boardSizeGroup.add(boardSize2);
        boardSizeGroup.add(boardSize3);
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
                if(button.getText().equals(" Other"))
                    return customizedBoardSize.getText();
                return button.getText();
            }
        }
        return null;
    }

    public ButtonGroup getDifficultyGroup() {
        return difficultyGroup;
    }

    public ButtonGroup getBoardSizeGroup() {
        return boardSizeGroup;
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
