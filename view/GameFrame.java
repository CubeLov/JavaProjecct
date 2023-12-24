package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame{
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private StartPanel startPanel;
    private SetPanel setPanel;
    private ChessGamePanel manualGamePanel;
    private OnlinePanel onlinePanel;
    private RegisterPanel registerPanel;
    private RankPanel rankPanel;
    private final int WIDTH;
    private final int HEIGTH;
    public GameFrame(int width, int height) {
        mainPanel=new JPanel();
        startPanel=new StartPanel(width,height);
        setPanel=new SetPanel(width,height);
        manualGamePanel=new ChessGamePanel(width,height);
        onlinePanel=new OnlinePanel(width,height);
        registerPanel=new RegisterPanel(width,height);
        rankPanel=new RankPanel(width,height);
        cardLayout=new CardLayout();
        mainPanel.setLayout(cardLayout);
        setRegisterPanel();
        setStartPanel();
        setSetPanel();
        setManualGamePanel();
        setOnlinePanel();
        setRankPanel();

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
        startPanel.getOnlineButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Online");
        });
        startPanel.getRankButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Rank");
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
        GameController gameController=new GameController(manualGamePanel.getChessboardComponent(),new Chessboard());
        manualGamePanel.setGameController(gameController);
        gameController.setStatusLabel(manualGamePanel.getStatusLabel());
        mainPanel.add(manualGamePanel,"ManualGame");
    }
    private void setOnlinePanel(){
        mainPanel.add(onlinePanel,"Online");
        GameController gameController=new GameController(onlinePanel.getChessboardComponent(),new Chessboard());
        onlinePanel.setGameController(gameController);
        gameController.setStatusLabel(onlinePanel.getStatusLabel());
        onlinePanel.setGameController(onlinePanel.getGameController());

        onlinePanel.getUpdateButton().addActionListener(e->{
            writeOnlineScore("records/score.txt");
        });
        onlinePanel.getBackButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Start");
        });
    }
    private void writeOnlineScore(String path){
        List<String> saveLines=new ArrayList<>();
        int score=onlinePanel.getScore();
        saveLines.add(registerPanel.getId());
        saveLines.add(Integer.toString(score));
        try {
            Files.write(Path.of(path),saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setRegisterPanel(){
        registerPanel.getConfirmButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Start");
            registerPanel.setId(registerPanel.getText());
        });
        mainPanel.add(registerPanel,"Register");
    }
    private void setRankPanel(){
        rankPanel.getBackButton().addActionListener(e->{
            cardLayout.show(mainPanel,"Start");
        });
        mainPanel.add(rankPanel,"Rank");
    }
    public String getName(){
        return registerPanel.getId();
    }
}
