package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private StartPanel startPanel;
    private SetPanel setPanel;
    private ChessGamePanel manualGamePanel;
    private final int WIDTH;
    private final int HEIGTH;
    public GameFrame(int width, int height) {
        mainPanel=new JPanel();
        startPanel=new StartPanel(width,height);
        setPanel=new SetPanel(width,height);
        manualGamePanel=new ChessGamePanel(width,height);
        cardLayout=new CardLayout();
        mainPanel.setLayout(cardLayout);
        setStartPanel();
        setSetPanel();
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
        startPanel.getManualButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Settings");
        });
        startPanel.getLoadButton().addActionListener(e->{
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            manualGamePanel.getGameController().loadGameFromFile(path);
            cardLayout.show(mainPanel,"ManualGame");
        });
        mainPanel.add(startPanel,"Start");
    }

    private void setSetPanel(){
        setPanel.getNextButton().addActionListener(e->{
            cardLayout.show(mainPanel,"ManualGame");
        });

        mainPanel.add(setPanel,"Settings");
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
