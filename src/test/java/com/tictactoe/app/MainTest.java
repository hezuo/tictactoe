package com.tictactoe.app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void printPlayfield_Valid() {
        String input = "3,2";
        int[] axis = Main.getAxisFromString(input);
        assertEquals(3, axis[0]);
        assertEquals(2, axis[1]);
    }

    @Test
    public void printPlayfield_Valid2() {
        String input = "  3 , 2 ";
        int[] axis = Main.getAxisFromString(input);
        assertEquals(3, axis[0]);
        assertEquals(2, axis[1]);
    }


    @Test
    public void printPlayfield_invalid() {
        String input = "  a , 2 ";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid move. Please choose use this format: 1,2");
        int[] axis = Main.getAxisFromString(input);
        assertEquals(3, axis[0]);
        assertEquals(2, axis[1]);
    }
}
