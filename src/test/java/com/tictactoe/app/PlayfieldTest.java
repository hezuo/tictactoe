package com.tictactoe.app;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class PlayfieldTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    char symbolPlayer1 = 'a';
    char symbolPlayer2 = 'b';
    char symbolComputer = 'c';

    @Test
    public void printPlayfield_Valid() {

        for(int playfieldSize = 3; playfieldSize <= 10; playfieldSize++) {
            Playfield playfield = createPlayfield(playfieldSize);
            assertEquals(playfieldSize, playfield.getPlayfield().length);
            assertEquals(playfieldSize, playfield.getPlayfield()[0].length);
        }
    }

    @Test
    public void printPlayfield_InvalidSize() {
        int[] indexes = new int[]{-1, 0, 1, 2, 11, 12, 13};

        for(int playfieldSize: indexes) {
            checkInvalidArgument(playfieldSize, "Invalid playfield size");
        }
    }

    @Test
    public void printPlayfieldInvalidCharacters() {
        int playfieldSize = 5;
        String invalidSymbol = "Invalid symbol";
        symbolPlayer1 = symbolPlayer2;
        checkInvalidArgument(playfieldSize, invalidSymbol);

        symbolPlayer1 = 'z';
        symbolPlayer2 = symbolComputer = 'w';
        checkInvalidArgument(playfieldSize, invalidSymbol);

        symbolPlayer2 = 'z';
        symbolPlayer1 = symbolComputer = 'w';
        checkInvalidArgument(playfieldSize, invalidSymbol);
    }

    @Test
    public void addMoveValid() {
        int playfieldSize = 10;
        Playfield playfield = createPlayfield(playfieldSize);
        playfield.addMove(1, new PlayfieldAxis(0,0));
        playfield.addMove(2, new PlayfieldAxis(1,0));
        playfield.addMove(3, new PlayfieldAxis(2,0));

        assertEquals(symbolPlayer1, playfield.getPlayfield()[0][0]);
        assertEquals(symbolPlayer2, playfield.getPlayfield()[1][0]);
        assertEquals(symbolComputer, playfield.getPlayfield()[2][0]);

        playfield.addMove(1, new PlayfieldAxis(0,1));
        playfield.addMove(2, new PlayfieldAxis(1,1));
        playfield.addMove(3, new PlayfieldAxis(2,1));

        assertEquals(symbolPlayer1, playfield.getPlayfield()[0][1]);
        assertEquals(symbolPlayer2, playfield.getPlayfield()[1][1]);
        assertEquals(symbolComputer, playfield.getPlayfield()[2][1]);

        playfield.addMove(1, new PlayfieldAxis(0,2));
        playfield.addMove(2, new PlayfieldAxis(1,2));
        playfield.addMove(3, new PlayfieldAxis(2,2));


        assertEquals(symbolPlayer1, playfield.getPlayfield()[0][2]);
        assertEquals(symbolPlayer2, playfield.getPlayfield()[1][2]);
        assertEquals(symbolComputer, playfield.getPlayfield()[2][2]);
    }

    @Test
    public void checkValidHorizontal() {
        int playfieldSize = 4;
        Playfield playfield = createPlayfield(playfieldSize);
        playfield.addMove(1, new PlayfieldAxis(0,3));
        playfield.addMove(1, new PlayfieldAxis(1,3));
        playfield.addMove(1, new PlayfieldAxis(3,3));
        playfield.addMove(2, new PlayfieldAxis(2,0));
        playfield.addMove(3, new PlayfieldAxis(1,1));

        assertFalse(playfield.checkWin());

        playfield.addMove(1, new PlayfieldAxis(2,3));

        assertTrue(playfield.checkWin());

    }

    @Test
    public void checkValidVertical() {
        int playfieldSize = 3;
        Playfield playfield = createPlayfield(playfieldSize);

        playfield.addMove(1, new PlayfieldAxis(0,0));
        playfield.addMove(2, new PlayfieldAxis(1,0));
        playfield.addMove(3, new PlayfieldAxis(2,0));

        playfield.addMove(1, new PlayfieldAxis(0,1));
        playfield.addMove(2, new PlayfieldAxis(1,1));
        playfield.addMove(3, new PlayfieldAxis(2,1));

        assertFalse(playfield.checkWin());

        playfield.addMove(1, new PlayfieldAxis(0,2));

        assertTrue(playfield.checkWin());
    }

    @Test
    public void checkValid_ValidDiagonal() {
        int playfieldSize = 4;
        Playfield playfield = createPlayfield(playfieldSize);

        playfield.addMove(1, new PlayfieldAxis(0,0));
        playfield.addMove(1, new PlayfieldAxis(1,1));
        playfield.addMove(1, new PlayfieldAxis(2,2));
        playfield.addMove(2, new PlayfieldAxis(1,0));
        playfield.addMove(3, new PlayfieldAxis(2,1));

        assertFalse(playfield.checkWin());

        playfield.addMove(1, new PlayfieldAxis(3,3));

        assertTrue(playfield.checkWin());
    }

    @Test
    public void checkValid_ValidDiagonalReverse() {
        int playfieldSize = 5;
        Playfield playfield = createPlayfield(playfieldSize);

        playfield.addMove(1, new PlayfieldAxis(4,0));
        playfield.addMove(1, new PlayfieldAxis(3,1));
        playfield.addMove(1, new PlayfieldAxis(2,2));
        playfield.addMove(1, new PlayfieldAxis(1,3));

        playfield.addMove(2, new PlayfieldAxis(1,0));
        playfield.addMove(3, new PlayfieldAxis(2,0));


        playfield.addMove(1, new PlayfieldAxis(1,1));

        playfield.addMove(2, new PlayfieldAxis(1,2));
        playfield.addMove(3, new PlayfieldAxis(2,1));

        assertFalse(playfield.checkWin());

        playfield.addMove(1, new PlayfieldAxis(0,4));
        playfield.addMove(1, new PlayfieldAxis(3,3));

        assertTrue(playfield.checkWin());
    }

    @Test
    public void addMove_InvalidPosition() {
        int playfieldSize = 10;
        Playfield playfield = createPlayfield(playfieldSize);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid position. Please choose another one.");

        playfield.addMove(1, new PlayfieldAxis(0,0));
        playfield.addMove(1, new PlayfieldAxis(0,0));

    }

     @Test
     public void addMove_InvalidPositionTaken() {
         int playfieldSize = 10;
         Playfield playfield = createPlayfield(playfieldSize);
         thrown.expect(IllegalArgumentException.class);
         thrown.expectMessage("Invalid position. Please choose another one.");

         playfield.addMove(1, new PlayfieldAxis(0,0));
         playfield.addMove(2, new PlayfieldAxis(0,0));
     }

     @Test
     public void addMove_InvalidPositionMax() {
         int playfieldSize = 4;
         Playfield playfield = createPlayfield(playfieldSize);
         thrown.expect(IllegalArgumentException.class);

         playfield.addMove(1, new PlayfieldAxis(6,7));

     }

    private Playfield createPlayfield(int playfieldSize){
        return  new Playfield(playfieldSize,
                symbolPlayer1,
                symbolPlayer2,
                symbolComputer);
    }

    private void checkInvalidArgument(int playfieldSize, String exceptionMessage){
        try {
            createPlayfield(playfieldSize);
            fail("Expected exception to be thrown");
        } catch (Exception e){
            assertThat(e, CoreMatchers.instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), containsString(exceptionMessage));
        }
    }


    @Test
    public void generateComputerMove() {
        int playfieldSize = 4;
        Playfield playfield = createPlayfield(playfieldSize);
        PlayfieldAxis axis = playfield.generateComputerMove();
        assertNotNull(axis.getX());
        assertNotNull(axis.getY());
    }
}