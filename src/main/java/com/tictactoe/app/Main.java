package com.tictactoe.app;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
public class Main {

    public static void main(String... args){
        String fileName = args[0];
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Properties properties = new Properties();
            properties.load(fis);

            Integer playgroundSize = Integer.parseInt(properties.getProperty("playgroundSize"), 10);
            Character player1Symbol = properties.getProperty("player1").charAt(0);
            Character player2Symbol = properties.getProperty("player2").charAt(0);
            Character computerSymbol = properties.getProperty("computer").charAt(0);

            boolean isGameFinished = false;
            Playfield playfield = new Playfield(playgroundSize, player1Symbol,player2Symbol, computerSymbol);

            int orderPlayer[] = new int[] {2,1,0};
            Scanner keyboard = new Scanner(System.in);

            while(!isGameFinished){
                for(int index = 0; index < orderPlayer.length && !isGameFinished; ) {
                    int currentPlayer  = orderPlayer[index];
                    Player player = playfield.getPlayers()[currentPlayer];

                    if(player.getPlayerName().equalsIgnoreCase("computer")) {
                        int[] computerMove = playfield.addComputerMove();
                        System.out.println("Computer played: " + (computerMove[0]+1) + " , " + (computerMove[1]+1));
                        System.out.println(playfield.printPlayfield());

                        index++;
                    } else {
                        System.out.println("Enter your move, " + player.getPlayerName());
                        try {
                            int[] axis = getAxisFromString(keyboard.next());
                            playfield.addMove(player.getPlayerNumber(), axis[0], axis[1]);

                            System.out.println(playfield.printPlayfield());
                            index++;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    isGameFinished = playfield.checkWin();
                    if(isGameFinished){
                        System.out.println(player.getPlayerName() +" won the game.");
                    }
                    if(playfield.getRemainingAvailableCells() < 1){
                        System.out.println(" Game finished.");
                        isGameFinished = true;
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int[] getAxisFromString(String next) throws IllegalArgumentException {
        try{
            String[] parts = next.split(",");
            int x = Integer.parseInt(parts[0].replaceAll("[^0-9]",""), 10);
            int y = Integer.parseInt(parts[1].replaceAll("[^0-9]",""), 10);
            return new int[]{x,y};
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid move. Please choose use this format: 1,2");
        }
    }
}
