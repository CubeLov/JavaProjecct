package view;

import javax.swing.*;
import java.awt.*;

public class OnlinePanel extends ChessGamePanel {
    JButton updateButton;
    public OnlinePanel(int width, int height) {
        super(width, height);
        addUpdateButton();
    }

    private void addUpdateButton() {
        updateButton=new JButton("Update");
        updateButton.setLocation(HEIGTH, HEIGTH / 10 + 440);
        updateButton.setSize(200, 60);
        updateButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(updateButton);
    }
    public int getScore(){
        return super.getGameController().getScore();
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
}
