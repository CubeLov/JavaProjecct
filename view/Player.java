package view;

import java.io.Serializable;

public class Player implements Comparable, Serializable {
    private String name;
    private int score;
    private static final long serialVersionUID=1L;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        Player t=(Player) o;
        if(score<t.getScore())
            return -1;
        if(score>t.getScore())
            return 1;
        return 0;
    }
}
