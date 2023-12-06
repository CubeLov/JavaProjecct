package model;

import javax.swing.plaf.PanelUI;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    // Cell->Piece
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }

    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }
    /*
    判断当前棋盘中是否有可消除的棋子
     */
    public boolean checkGrid(){
        int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
        int col=Constant.CHESSBOARD_COL_SIZE.getNum();
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++){
                if(isInGrid(i,j-1)&&isInGrid(i,j+1)){
                    if(grid[i][j-1].equals(grid[i][j])&&grid[i][j+1].equals(grid[i][j]))
                        return true;
                }
                if(isInGrid(i-1,j)&&isInGrid(i+1,j)){
                    if(grid[i-1][j].equals(grid[i][j])&&grid[i+1][j].equals(grid[i][j]))
                        return true;
                }
            }
        return false;
    }
    private boolean isInGrid(int row,int col){
        if(row<0||row>=Constant.CHESSBOARD_ROW_SIZE.getNum())
            return false;
        if(col<0||row>=Constant.CHESSBOARD_COL_SIZE.getNum())
            return false;
        return true;
    }
    /*
    selectPoint保证是相邻的，只需要判断交换后是否可以消除即可
     */
    public boolean canSwap(ChessboardPoint point1, ChessboardPoint point2){
        swapChessPiece(point1,point2);
        boolean flag=checkGrid();
        swapChessPiece(point1,point2);
        return flag;
    }

    public Cell[][] getGrid() {
        return grid;
    }



}
