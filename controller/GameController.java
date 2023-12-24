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

    private JLabel statusLabel;

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.opt=-1;
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void initialize() {
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }
    public void restartGame(){
        this.score=0;
        this.opt=-1;
        this.selectedPoint=this.selectedPoint2=null;
        this.statusLabel.setText("Score:" + score);
    }
    @Override
    public void onPlayerSwapChess() {
        // TODO: Init your swap function here.
        System.out.println("Implement your swap here.");
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
            }
        }
    }
    /*
    更新分数，在窗口中清除已消除棋子
     */
    private void updateNull(){
        score+=10*model.eliminateGrid();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++)
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (model.getVisAtGrid(point)) {
                    view.removeChessComponentAtGrid(point);
                    view.repaintChessComponentAtGrid(point);
                }
            }
        opt=1;
    }
    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        switch (opt){
            case 1:
                model.fallDown();
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++)
                    for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                        ChessboardPoint point = new ChessboardPoint(i, j);
                        if(!model.getVisAtGrid(point))
                            view.removeChessComponentAtGrid(point);
                        view.repaintChessComponentAtGrid(point);
                    }
                model.clearVis();
                view.initiateChessComponent(model);
                view.repaintAllChessComponent();
                opt=2;
                break;
            case 2:
                int[] upPosition=new int[Constant.CHESSBOARD_COL_SIZE.getNum()];
                for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                    upPosition[j]=model.getTopPositionAtCol(j);
                }
                model.regeneratePieces();
                for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++)
                    for(int i=upPosition[j];i>=0;i--){
                        ChessboardPoint point = new ChessboardPoint(i, j);
                        view.setChessComponentAtGrid(point,new ChessComponent(view.getCHESS_SIZE(),model.getChessPieceAt(point)));
                        view.repaintChessComponentAtGrid(point);
                    }
                if(model.checkGrid())
                    opt=3;
                else //TODO:增加没有可以消除棋子的弹窗
                    opt=1;
                break;
            case 3:
                updateNull();
                break;
        }


        System.out.println("Implement your next step here.");
        this.statusLabel.setText("Score:" + score);

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
        List<String> saveLines=model.convertBoardToList();
        try {
            Files.write(Path.of(path),saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameFromFile(String path) {
        try {
            List<String> loadLines= Files.readAllLines(Path.of(path));
            model.convertListToBoard(loadLines);
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getScore() {
        return score;
    }
}
