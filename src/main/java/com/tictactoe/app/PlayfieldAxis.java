package com.tictactoe.app;

public class PlayfieldAxis {
    private int x;
    private int y;

    public PlayfieldAxis(int x, int y) {
        this.x = x;
        this.y = y;

        if(x < 0 || y < 0){
            throw new IllegalArgumentException(String.format("wrong move: x: %s y: %s", x, y));
        }
    }

    public int getX() {
        return x;
    }

    public int getXForMove(){
        return x+1;
    }

    public int getYForMove(){
        return y+1;
    }

    public int getY() {
        return y;
    }
}
