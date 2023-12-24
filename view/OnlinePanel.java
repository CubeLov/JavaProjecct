package view;

import javax.swing.*;
import java.awt.*;

public class OnlinePanel extends ChessGamePanel {
    private JButton updateButton;
    private JButton backButton;
    public OnlinePanel(int width, int height) {
        super(width, height);
        addUpdateButton();
        addBackButton();
    }

    private void addUpdateButton() {
        updateButton=new JButton("Update");
        updateButton.setLocation(HEIGTH, HEIGTH / 10 + 440);
        updateButton.setSize(200, 60);
        updateButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(updateButton);
    }
    private void addBackButton(){
        backButton=new JButton("Back");
        backButton.setLocation(HEIGTH, HEIGTH / 10 + 520);
        backButton.setSize(200, 60);
        backButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(backButton);
    }
    public int getScore(){
        return super.getGameController().getScore();
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
