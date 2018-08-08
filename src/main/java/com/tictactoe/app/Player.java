package com.tictactoe.app;

public class Player {
    private int playerNumber;
    private char symbol;
    private String playerName;

    public Player(int playerNumber, char symbol, String playerName) {
        this.playerNumber = playerNumber;
        this.symbol = symbol;
        this.playerName = playerName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public char getSymbol() {
        return symbol;
    }


}
