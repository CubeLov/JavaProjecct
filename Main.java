import Net.PlayMusic;
import controller.GameController;
import model.Chessboard;
import view.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GameFrame mainFrame = new GameFrame(1100, 810);
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("TTT");
        });
    }
}
