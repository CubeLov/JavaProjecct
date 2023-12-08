package model;

import java.util.Arrays;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    // Cell->Piece
    private Cell[][] grid;
    boolean[][] visRow;
    boolean[][] visCol;
    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        visRow=new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        visCol=new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
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
    /*
    消除所有可消除的棋子，并返回消除棋子个数
     */
    public int eliminateGrid(){
        int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
        int col=Constant.CHESSBOARD_COL_SIZE.getNum();
        for(int i=0;i<row;i++){
            Arrays.fill(visRow[i],false);
            Arrays.fill(visCol[i],false);
        }
        int cnt=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(isInGrid(i,j-1)&&isInGrid(i,j+1)){
                    if(grid[i][j-1].equals(grid[i][j])&&grid[i][j+1].equals(grid[i][j]))
                        visRow[i][j]=visRow[i][j-1]=visRow[i][j+1]=true;
                }
                if(isInGrid(i-1,j)&&isInGrid(i+1,j)){
                    if(grid[i-1][j].equals(grid[i][j])&&grid[i+1][j].equals(grid[i][j])){
                        visCol[i][j]=visCol[i-1][j]=visCol[i+1][j]=true;
                    }
                }
            }
        }
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++){
                if(visRow[i][j])
                    cnt++;
                if(visCol[i][j])
                    cnt++;
                if(visRow[i][j]||visCol[i][j])
                    grid[i][j]=null;
            }
        return cnt;
    }
    public boolean getVisAtGrid(ChessboardPoint point){
        return visRow[point.getRow()][point.getCol()]||visCol[point.getRow()][point.getCol()];
    }
    private boolean isInGrid(int row,int col){
        if(row<0||row>=Constant.CHESSBOARD_ROW_SIZE.getNum())
            return false;
        if(col<0||col>=Constant.CHESSBOARD_COL_SIZE.getNum())
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
