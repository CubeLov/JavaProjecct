package model;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell implements Serializable {
    // the position for chess
    private ChessPiece piece;


    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        Cell temp = (Cell) obj;
        return temp.getPiece().equals(this.getPiece());
    }
}
