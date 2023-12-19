import controller.GameController;
import model.Chessboard;
import view.AddWindows;
import view.ChessGameFrame;
import view.PlayMusic;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddWindows addWindows = new AddWindows();
            new Thread(()->{while(true) {PlayMusic.playMusic();} //while中的true可换成参数来控制音乐的停止播放
            }).start();// Lambda表达式
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            addWindows.chessGameFrame = mainFrame;
            gameController.setStatusLabel(mainFrame.getStatusLabel());
            mainFrame.setVisible(true);
        });
    }
}
