package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RankPanel extends JPanel {
    protected final int WIDTH;
    protected final int HEIGTH;
    private DefaultListModel<String> rankModel;
    private JList<String> rankList;
    private JScrollPane scrollPane;
    private JLabel label;
    private List<Player> players;
    private JButton backButton;

    public RankPanel(int width,int height){
        this.WIDTH = width;
        this.HEIGTH = height;
        setPreferredSize(new Dimension(width,height));
        setBounds(0,0,width,height);
        setLayout(null);
        rankModel=new DefaultListModel<>();
        rankList=new JList<>(rankModel);
        rankList.setCellRenderer(new CustomTextRenderer());
        scrollPane=new JScrollPane(rankList);
        scrollPane.setBounds(0,150,width,height-200);
        UIManager.put("ScrollBar.width",100);
        add(scrollPane);
        addLabel();
        addBackButton();
        players=new ArrayList<>();
        new Thread(()->{
            while (true){
                try {
                    readRank("records/rank.txt");
                    updateRankBoard();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    private void readRank(String path){
        try {
            List<String> loadLines= Files.readAllLines(Path.of(path));
            if(loadLines.isEmpty())
                return;
            else if(loadLines.get(0).isEmpty())
                return ;
            else{
                players.clear();
                for (String loadLine : loadLines) {
                    String[] t = loadLine.split(" ");
                    players.add(new Player(t[0], Integer.parseInt(t[1])));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRankBoard(){
        Collections.sort(players);
        rankModel.clear();
        for (Player player : players) {
            rankModel.addElement(player.getName()+" :   "+player.getScore()+" points");
        }
    }
    private void addLabel(){
        label=new JLabel("Who Is Number 1");
        label.setFont((new Font("Rockwell", Font.BOLD, 50)));
        label.setBounds(WIDTH/2-300,0,600,150);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
    private void addBackButton(){
        backButton=new JButton("Back");
        backButton.setFont((new Font("Rockwell", Font.PLAIN, 25)));
        backButton.setFocusPainted(false);
        backButton.setBounds(50,35,100,80);
        add(backButton);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        RankPanel s=new RankPanel(1100,810);
        s.getPlayers().add(new Player("a",10));
        s.getPlayers().add(new Player("b",50));
        s.getPlayers().add(new Player("c",20));
        s.getPlayers().add(new Player("d",40));
        s.getPlayers().add(new Player("e",30));
        s.getPlayers().add(new Player("e",30));
        s.updateRankBoard();
        JFrame frame=new JFrame("Test");
        frame.setSize(1100,810);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //frame.setContentPane(s);
        frame.setVisible(true);
    }

}
class CustomTextRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont((new Font("Rockwell", Font.BOLD, 30)));
        return this;
    }
}
