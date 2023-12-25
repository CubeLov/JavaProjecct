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
    public int getNumber(){
        if(name.equals("💎")) return 1;
        if(name.equals("⚪")) return 2;
        if(name.equals("▲")) return 3;
        if(name.equals("🔶")) return 4;
        return 0;
    }
    public static String getNameFromNum(int num){
        if(num==1) return "💎";
        if(num==2) return "⚪";
        if(num==3) return "▲";
        if(num==4) return "🔶";
        return null;
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
