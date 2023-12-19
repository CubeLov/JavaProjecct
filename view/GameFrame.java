package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel startPanel;
    private JPanel difficultyPanel;
    private ChessGamePanel manualGamePanel;
    private final int WIDTH;
    private final int HEIGTH;
    public GameFrame(int width, int height) {
        mainPanel=new JPanel();
        startPanel=new JPanel();
        difficultyPanel=new JPanel();
        manualGamePanel=new ChessGamePanel(width,height);
        cardLayout=new CardLayout();
        mainPanel.setLayout(cardLayout);
        setStartPanel();
        setDifficultyPanel();
        setManualGamePanel();

        this.WIDTH = width;
        this.HEIGTH = height;

        mainPanel.setSize(WIDTH,HEIGTH);
        setTitle("2023 CS109 Project Demo"); //设置标题
        setContentPane(mainPanel);
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
    }
    private void setStartPanel(){
        startPanel.setSize(WIDTH,HEIGTH);
        startPanel.setLayout(new BorderLayout());
        JLabel label=new JLabel("Match-3 Games");
        label.setFont((new Font("Rockwell", Font.BOLD, 20)));

        JButton manualButton=new JButton("Manual Mode");
        manualButton.addActionListener(e->{
            cardLayout.show(mainPanel,"Difficulty");
        });

        startPanel.add(manualButton,BorderLayout.SOUTH);
        startPanel.add(label,BorderLayout.CENTER);

        mainPanel.add(startPanel,"Start");
    }

    private void setDifficultyPanel(){
        difficultyPanel.setSize(WIDTH,HEIGTH);
        JButton nextButton=new JButton("Next");
        nextButton.addActionListener(e->{
            cardLayout.show(mainPanel,"ManualGame");
        });

        difficultyPanel.setLayout(new BorderLayout());
        difficultyPanel.add(nextButton,BorderLayout.CENTER);

        mainPanel.add(difficultyPanel,"Difficulty");
    }
    private void setManualGamePanel(){
        manualGamePanel.setBackground(Color.GRAY);
        mainPanel.add(manualGamePanel,"ManualGame");
    }

    public ChessboardComponent getChessboardComponent() {
        return manualGamePanel.getChessboardComponent();
    }

    public void setGameController(GameController gameController) {
        manualGamePanel.setGameController(gameController);
    }

    public JLabel getStatusLabel() {
        return manualGamePanel.getStatusLabel();
    }
}
