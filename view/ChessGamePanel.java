package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGamePanel extends JPanel {
    //    public final Dimension FRAME_SIZE ;
    protected final int WIDTH;
    protected final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;
    protected JButton backButton;

    public ChessGamePanel(int width, int height) {
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLayout(null);

        addChessboard();
        addLabel();
        addRestartButton();
        addSwapConfirmButton();
        addNextStepButton();
        addSaveButton();
        addShuffleButton();
        addBackButton();
        addHintButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        this.statusLabel = new JLabel("Score:0");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10-30);
        statusLabel.setSize(200, 30);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.setLocation(HEIGTH, HEIGTH / 10 + 30);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.setLocation(HEIGTH, HEIGTH / 10 + 110);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 350);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.saveGameToFile(path);
        });
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            gameController.restartGame();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 270);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addShuffleButton(){
        JButton button = new JButton("Shuffle");
        button.setLocation(HEIGTH, HEIGTH / 10 + 190);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            gameController.shuffle();
        });
    }
    public void addBackButton(){
        backButton=new JButton("Back");
        backButton.setLocation(HEIGTH, HEIGTH / 10 + 430);
        backButton.setSize(200, 60);
        backButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        backButton.addActionListener(e->{
            gameController.restartGame();
        });
        add(backButton);
    }

    public JButton getBackButton() {
        return backButton;
    }

    private void addHintButton(){
        JButton button = new JButton("Hint");
        button.setLocation(HEIGTH, HEIGTH/10+510);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            gameController.getHint();
        });
    }

    public static void main(String[] args) {

        ChessGamePanel s=new ChessGamePanel(1100,810);
        JFrame frame=new JFrame("Test");
        frame.setSize(1100,810);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //frame.setContentPane(s);
        frame.setVisible(true);
    }
}
