package model;


import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;

    public ChessPiece(String name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        ChessPiece temp = (ChessPiece) obj;
        return this.name.equals(temp.name);
    }

}
