package model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    // Cell->Piece
    private Cell[][] grid;
    StringBuilder sb;
    boolean flag = true;
    private Cell[][] newGrid;
    boolean[][] visRow;
    boolean[][] visCol;
    public Chessboard() {
        sb=new StringBuilder();
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        this.newGrid =
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
                newGrid[i][j]=new Cell();
            }
        }
    }

    public void initPieces() {

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        while(checkGrid()){
            eliminateGrid();
            fallDown();
            regeneratePieces();
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
        int cnt=0;
        clearVis();
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
                    grid[i][j].setPiece(null);
            }
        return cnt;
    }
    public void clearVis(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            Arrays.fill(visRow[i],false);
            Arrays.fill(visCol[i],false);
        }
    }
    private void clearNewGrid(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++)
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++)
                newGrid[i][j].setPiece(null);
    }
    public void fallDown(){
        clearNewGrid();
        int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
        int col=Constant.CHESSBOARD_COL_SIZE.getNum();
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++)
                visRow[i][j]|=visCol[i][j];//让visRow中存储所有的vis信息

        for(int j=0;j<col;j++){
            int tot=0;
            for(int i=row-1;i>=0;i--){
                if(!visRow[i][j])
                    newGrid[row-(++tot)][j].setPiece(grid[i][j].getPiece());
            }
        }
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++){
                if(newGrid[i][j].getPiece()!=null)
                    grid[i][j].setPiece(newGrid[i][j].getPiece());
                else
                    grid[i][j].setPiece(null);
            }

    }
    public int getTopPositionAtCol(int col){
        for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--)
            if(grid[i][col].getPiece()==null)
                return i;
        return 0;
    }
    public void regeneratePieces(){
        int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
        int col=Constant.CHESSBOARD_COL_SIZE.getNum();
        for(int j=0;j<col;j++){
            for(int i=row-1;i>=0;i--){
                if(grid[i][j].getPiece()==null){
                    grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }
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


    public List<String> convertBoardToList() {
        List<String>saveLines=new ArrayList<>();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            sb.setLength(0);
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessPiece piece=grid[i][j].getPiece();
                if(piece!=null){
                    sb.append(piece.getName()).append(" ");
                }else {
                    sb.append("0 ");
                }
            }
            saveLines.add(sb.toString());
        }
        sb.setLength(0);
        return saveLines;
    }

    public void convertListToBoard(List<String> loadLines) {
        for (int i = 0; i < loadLines.size(); i++) {
            String[] elements = loadLines.get(i).split(" ");
            for (int j = 0; j < elements.length; j++) {
                grid[i][j].setPiece(new ChessPiece(elements[j]));
            }
        }
    }
}
