package controller;

import listener.GameListener;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private int opt;


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;
    public static int level;
    private int step;
    private int cntShuffle;
    private JLabel statusLabel;
    public final String mode;

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public GameController(ChessboardComponent view, Chessboard model, String mode) {
        this.view = view;
        this.model = model;
        this.mode = mode;
        this.opt = -1;
        this.step = 0;
        this.score = 0;
        this.cntShuffle=0;
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void initialize() {
        if (opt != -1) {
            return;
        }
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    public void restartGame() {
        if (opt != -1) {
            JOptionPane.showMessageDialog(null, "Please press Nextstep button", "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        this.score = 0;
        this.step = 0;
        this.cntShuffle=0;
        this.selectedPoint = this.selectedPoint2 = null;
        this.statusLabel.setText("Score:" + score);
        initialize();
    }
    public void shuffle(){
        if(++cntShuffle<=3){
            String str=String.format("Shuffle successful\nShuffle cnt: "+cntShuffle);
            JOptionPane.showMessageDialog(null, str, "Hint", JOptionPane.INFORMATION_MESSAGE);
            initialize();
        }
        else{
            JOptionPane.showMessageDialog(null, "No more shuffle chances", "Hint", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public void gameFailed() {
        JOptionPane.showMessageDialog(null, "Game Failed", "Hint", JOptionPane.INFORMATION_MESSAGE);

        int choice = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Restart", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            JOptionPane.showMessageDialog(null, "Game Over", "Hint", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void gamePassed() {
        JOptionPane.showMessageDialog(null, "Game Passed", "Hint", JOptionPane.INFORMATION_MESSAGE);
        int choice = JOptionPane.showConfirmDialog(null, "Go to next level?", "Choose", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            if (level + 1 > 3) {
                JOptionPane.showMessageDialog(null, "You win the final game!", "Hint", JOptionPane.INFORMATION_MESSAGE);
            } else {
                level++;
                restartGame();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Game Over", "Hint", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void gameFinished() {
        JOptionPane.showMessageDialog(null, "Game Finished\nPlease update your grade", "Hint", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerSwapChess() {
        // TODO: Init your swap function here.
        System.out.println("Implement your swap here.");
        if (opt != -1) {
            JOptionPane.showMessageDialog(null, "Please press Nextstep button", "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (selectedPoint != null && selectedPoint2 != null) {
            if (model.canSwap(selectedPoint, selectedPoint2)) {//判断是否可以交换(交换后是否可消除)
                //model层交换
                model.swapChessPiece(selectedPoint, selectedPoint2);
                // remove view层中的两个棋子
                ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
                ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
                // set view 中的新棋子
                view.setChessComponentAtGrid(selectedPoint, chess2);
                view.setChessComponentAtGrid(selectedPoint2, chess1);
                //重新绘制
                chess1.repaint();
                chess2.repaint();
                selectedPoint = null;
                selectedPoint2 = null;
                updateNull();
                //步数加一
                step++;
            } else {
                if (!model.checkGrid()) {
                    JOptionPane.showMessageDialog(null, "Dead end. Please shuffle", "Hint", JOptionPane.INFORMATION_MESSAGE);

                } else
                    JOptionPane.showMessageDialog(null, "Invalid exchange", "Hint", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select two pieces", "Hint", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /*
    更新分数，在窗口中清除已消除棋子
     */
    private void updateNull() {
        score += 10 * model.eliminateGrid();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++)
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (model.getVisAtGrid(point)) {
                    view.removeChessComponentAtGrid(point);
                    view.repaintChessComponentAtGrid(point);
                }
            }
        opt = 1;
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        switch (opt) {
            case 1:
                model.fallDown();
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++)
                    for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                        ChessboardPoint point = new ChessboardPoint(i, j);
                        if (!model.getVisAtGrid(point))
                            view.removeChessComponentAtGrid(point);
                        view.repaintChessComponentAtGrid(point);
                    }
                model.clearVis();
                view.initiateChessComponent(model);
                view.repaintAllChessComponent();
                selectedPoint = null;
                selectedPoint2 = null;
                opt = 2;
                break;
            case 2:
                int[] upPosition = new int[Constant.CHESSBOARD_COL_SIZE.getNum()];
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    upPosition[j] = model.getTopPositionAtCol(j);
                }
                model.regeneratePieces();
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++)
                    for (int i = upPosition[j]; i >= 0; i--) {
                        ChessboardPoint point = new ChessboardPoint(i, j);
                        view.setChessComponentAtGrid(point, new ChessComponent(view.getCHESS_SIZE(), model.getChessPieceAt(point)));
                        view.repaintChessComponentAtGrid(point);
                    }
                if (model.checkGrid())
                    opt = 3;
                else {
                    opt = -1;
                }
                break;
            case 3:
                updateNull();
                break;
            case -1:
                JOptionPane.showMessageDialog(null, "No pieces can be eliminated", "Hint", JOptionPane.INFORMATION_MESSAGE);
                break;
        }

        System.out.println(step + "   " + opt);
        System.out.println("Implement your next step here.");
        this.statusLabel.setText("Score:" + score);
        checkGame();
    }

    private void checkGame() {
        if (opt == -1 && step == 5) {
            if (mode.equals("Manual")) {
                switch (level) {
                    case 1:
                        if (score >= 100)
                            gamePassed();
                        else
                            gameFailed();
                        break;
                    case 2:
                        if (score >= 300)
                            gamePassed();
                        else
                            gameFailed();
                        break;
                    case 3:
                        if (score >= 800)
                            gamePassed();
                        else
                            gameFailed();
                        break;
                }
            } else if (mode.equals("Online")) {
                gameFinished();
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {//已经有两个选择点，此时point为点击的第三个点
            //第三个点和point1的距离
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            //第三个点和point2的距离
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {//第三个点为point1，取消选择point1
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {//第三个点为point2，取消选择point2
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {//第三个点为另一个与point1相邻的点
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {//第三个点为另一个与point2相邻的点
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {//选择第一个棋子并画圈
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {//再次点击已选中的点，取消选择
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {//选中第二个相邻点，合法
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {//距离过远，取消第一个选择点，将第二个选择点重设为第一个选择点
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }

    public void saveGameToFile(String path) {
        List<String> saveLines = model.convertBoardToList();
        saveLines.add(Integer.toString(step));
        saveLines.add(Integer.toString(level));
        saveLines.add(Integer.toString(cntShuffle));
        saveLines.add(Integer.toString(score));
        for (String saveLine : saveLines) {
            System.out.println(saveLine);
        }
        try {
            Files.write(Path.of(path), saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        // 检查文件名是否包含扩展名
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        } else {
            return ""; // 没有扩展名
        }
    }
    public boolean loadGameFromFile(String path) {
        Path tPath = Paths.get(path);
        // 获取文件名
        String fileName = tPath.getFileName().toString();
        // 获取文件扩展名
        String fileExtension = getFileExtension(fileName);
        if(!fileExtension.equals("txt")){
            JOptionPane.showMessageDialog(null, "File format error\nError code: 101", "Hint", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            List<String> loadLines = Files.readAllLines(Path.of(path));
            boolean flag=false;
            for(int i=0;i<loadLines.size()-4;i++){
                String str=loadLines.get(i);
                String x=str.replaceAll("\\s+","");
                for(int j=0;j<x.length();j++){
                    char c=x.charAt(j);
                    boolean imageCheck=false;
                    if(c=='1')
                        imageCheck=true;
                    else if(c=='2')
                        imageCheck=true;
                    else if(c=='3')
                        imageCheck=true;
                    else if(c=='4')
                        imageCheck=true;
                    if(!imageCheck){
                        JOptionPane.showMessageDialog(null, "Picture error\nError code: 103", "Hint", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                }
                if(x.length()!=8){
                    flag=true;
                    break;
                }
            }
            if(loadLines.size()!=12||flag){
                JOptionPane.showMessageDialog(null, "Board error\nError code: 102", "Hint", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            model.convertListToBoard(loadLines);
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();
            this.step = Integer.parseInt(loadLines.get(loadLines.size() - 4));
            level = Integer.parseInt(loadLines.get(loadLines.size() - 3));
            this.cntShuffle=Integer.parseInt(loadLines.get(loadLines.size() - 2));
            this.score=Integer.parseInt(loadLines.get(loadLines.size() - 1));
            this.statusLabel.setText("Score:" + score);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public void getHint() {

        ChessboardPoint[] res = model.hint();
        String str = String.format("Please swap " + res[0] + " " + res[1]);
        JOptionPane.showMessageDialog(null, str, "Hint", JOptionPane.INFORMATION_MESSAGE);

        if (selectedPoint != null) {
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            point1.setSelected(false);
            point1.repaint();
            selectedPoint = null;
        }
        if (selectedPoint2 != null) {
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            point2.setSelected(false);
            point2.repaint();
            selectedPoint2 = null;
        }
        selectedPoint = res[0];
        selectedPoint2 = res[1];

        var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
        var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
        point1.setSelected(true);
        point1.repaint();
        point2.setSelected(true);
        point2.repaint();
    }

    public int getScore() {
        return score;
    }
}
