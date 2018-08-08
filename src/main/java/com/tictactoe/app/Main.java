package com.tictactoe.app;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String... args){

        try {
            Playfield playfield = setupPlayField(args);

            int[] randomOrderPlayer = generateRandomOrderPlayers();
            Scanner keyboard = new Scanner(System.in);
            while(playfield.getRemainingAvailableCells() > 0){
                for(int index = 0; index < randomOrderPlayer.length && playfield.getRemainingAvailableCells() >0;) {
                    int currentPlayer  = randomOrderPlayer[index];
                    Player player = playfield.getPlayers()[currentPlayer];

                    if(player.getPlayerName().equalsIgnoreCase("computer")) {

                        PlayfieldAxis computerMove = playfield.addComputerMove();
                        System.out.println("Computer played: " + computerMove.getXForMove() + " , " + computerMove.getYForMove());
                        System.out.println(playfield.printPlayfield());
                        index++;
                    } else {
                        System.out.println("Enter your move, " + player.getPlayerName());
                        try {
                            PlayfieldAxis playfieldAxis = getAxisFromString(keyboard.next());
                            playfield.addMove(player.getPlayerNumber(), playfieldAxis);

                            System.out.println(playfield.printPlayfield());
                            index++;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    if(playfield.checkWin()){
                        System.out.println(player.getPlayerName() +" won the game.");
                        System.exit(0);
                    }
                }
            }

            System.out.println(" Game finished. No winner");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int[] generateRandomOrderPlayers() {
        if(ThreadLocalRandom.current().nextBoolean()){
            return new int[]{2,1,0};
        } else if(ThreadLocalRandom.current().nextBoolean()){
            return new int[]{1,2,0};
        } else if(ThreadLocalRandom.current().nextBoolean()){
            return new int[]{1,0,2};
        } else if(ThreadLocalRandom.current().nextBoolean()){
            return new int[]{0,1,2};
        } else if(ThreadLocalRandom.current().nextBoolean()){
            return new int[]{2,0,1};
        }
        return new int[]{0,2,1};
    }

    private static Playfield setupPlayField(String... args) throws Exception{
        try{
            String fileName = args[0];
            FileInputStream fis = new FileInputStream(fileName);
            Properties properties = new Properties();
            properties.load(fis);

            Integer playgroundSize = Integer.parseInt(properties.getProperty("playgroundSize"), 10);
            Character player1Symbol = properties.getProperty("player1").charAt(0);
            Character player2Symbol = properties.getProperty("player2").charAt(0);
            Character computerSymbol = properties.getProperty("computer").charAt(0);

            return new Playfield(playgroundSize, player1Symbol,player2Symbol, computerSymbol);

        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new Exception("Argument Filename is mandatory");

        } catch (Exception ex) {
            throw new Exception("Invalid configuration file");
        }
    }

    public static PlayfieldAxis getAxisFromString(String next) throws IllegalArgumentException {
        try{
            String[] parts = next.split(",");
            int x = Integer.parseInt(parts[0].replaceAll("[^0-9]",""), 10);
            int y = Integer.parseInt(parts[1].replaceAll("[^0-9]",""), 10);
            return new PlayfieldAxis(--x, --y);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid move. Please choose use this format: 1,2");
        }
    }
}
