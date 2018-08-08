package com.tictactoe.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Playfield {
    private char[][] playfield;
    private Player[] players = new Player[3];
    private Player computerPlayer;
    private Map<Integer, Character> symbols = null;
    private int remainingAvailableCells = 0;
    public static final char EMPTY_CHAR = ' ';



    public Playfield(int playfieldSize,
                     char symbolPlayer1,
                     char symbolPlayer2,
                     char symbolComputer) throws IllegalArgumentException {

        validateValues(playfieldSize, symbolPlayer1, symbolPlayer2, symbolComputer);
        this.playfield = new char[playfieldSize][playfieldSize];
        remainingAvailableCells = playfieldSize * playfieldSize;
        setupPlayfield();
        computerPlayer = new Player(3, symbolComputer, "computer");
        players[0] = new Player(1, symbolPlayer1, "player 1");
        players[1] = new Player(2, symbolPlayer2, "player 2");
        players[2] = computerPlayer;
    }

    private void validateValues(int playfieldSize, char symbolPlayer1, char symbolPlayer2, char symbolComputer) {
        if(!(playfieldSize >= 3 && playfieldSize <= 10)) {
            throw new IllegalArgumentException(String.format("Invalid playfield size: %d", playfieldSize));
        }
        if(symbolPlayer1 == symbolPlayer2
                || symbolPlayer1 == symbolComputer
                || symbolPlayer2 == symbolComputer) {
            throw new IllegalArgumentException(String.format("Invalid symbols: %s, %s, %s", symbolPlayer1, symbolPlayer2, symbolComputer));
        }
    }

    private void setupPlayfield(){
        for(char[] playfieldCurrent: playfield){
            Arrays.fill(playfieldCurrent, EMPTY_CHAR);
        }
    }

    public String printPlayfield() {
        StringBuilder sb = new StringBuilder();

        for(int indexY = 0; indexY < playfield.length; indexY++){
            StringBuilder line = new StringBuilder();
            StringBuilder separator = new StringBuilder();
            for(int indexX = 0; indexX < playfield.length; indexX++){
                separator.append("----");
                line.append(" " + playfield[indexY][indexX] + " |");
            }

            sb.append(separator);
            sb.append("\n");
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }

    public char getSymbolByPlayerIndex(int playerIndex){
        if( symbols == null) {
            symbols = new HashMap<>();
            for(Player player: players) {
                symbols.put(player.getPlayerNumber(), player.getSymbol());
            }
        }

        if(symbols.containsKey(playerIndex)){
            return symbols.get(playerIndex);
        }

        return EMPTY_CHAR;
    }

    public void addMove(int playerIndex, PlayfieldAxis playfieldAxis) {
        char inputChar = getSymbolByPlayerIndex(playerIndex);

        if (playfieldAxis.getX() < 0 || playfieldAxis.getY() < 0
                || playfieldAxis.getX() > playfield.length || playfieldAxis.getY() > playfield.length
                || getChar(playfieldAxis) != EMPTY_CHAR
        ) {
            throw new IllegalArgumentException("Invalid position. Please choose another one.");
        }

        --remainingAvailableCells;

        setChar(playfieldAxis, inputChar);
    }

    private void setChar(PlayfieldAxis playfieldAxis, char inputChar) {
        playfield[playfieldAxis.getX()][playfieldAxis.getY()] = inputChar;
    }


    public char[][] getPlayfield() {
        return playfield;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean checkWin() {

        int x = 0;
        boolean isWin = false;
        boolean isDiagonal = true;
        boolean isDiagonalReverse = true;

        int length = playfield.length;

        while(x < length && !isWin ){
            boolean horizontal = true;
            boolean vertical = true;
            int y = 0;
            while(y < length && !isWin ){

                if(!isWin && y+1 < length && vertical){
                    if (playfield[y][x] == playfield[y+1][x]
                            && playfield[y][x] != EMPTY_CHAR ) {
                        isWin = (y+1 == length-1);
                    } else {
                        vertical = false;
                    }
                }
                if(!isWin && y+1 < length && horizontal){
                    if (playfield[x][y] == playfield[x][y+1]
                            && playfield[x][y] != EMPTY_CHAR ) {
                        isWin = (y+1 == length-1);
                    } else {
                        horizontal = false;
                    }
                }

                if(!isWin && x+1 < length && isDiagonal) {
                    if( playfield[x][x] == playfield[x+1][x+1]
                            && playfield[x][x] != EMPTY_CHAR ) {
                        isWin = ( x+1 == length-1);
                    } else {
                        isDiagonal = false;
                    }
                }
                if(!isWin && length-x-2 > -1 && isDiagonalReverse) {
                    int current = length - x -1;
                    if( playfield[x][current] == playfield[x+1][current-1]
                            && playfield[x][current] != EMPTY_CHAR ) {
                        isWin = current-1 == 0;
                    } else {
                        isDiagonalReverse = false;
                    }
                }
                y++;
            }

            x++;
        }

        return isWin;
    }

    public char getChar(PlayfieldAxis playfieldAxis){
        return playfield[playfieldAxis.getX()][playfieldAxis.getY()];
    }

    public PlayfieldAxis addComputerMove() {
        PlayfieldAxis playfieldAxis = generateComputerMove();

        while(getChar(playfieldAxis) != EMPTY_CHAR){
            playfieldAxis = generateComputerMove();
        }

        addMove(computerPlayer.getPlayerNumber(), playfieldAxis);

        return playfieldAxis;
    }

    public PlayfieldAxis generateComputerMove() {
        int x = generateRandomNumber();
        int y = generateRandomNumber();

       return new PlayfieldAxis(x,y);
    }

    private int generateRandomNumber(){
        Random r = new Random();
        return r.nextInt(playfield.length);
    }

    public int getRemainingAvailableCells() {
        return remainingAvailableCells;
    }
}
