package Stratego.logic.src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    @BeforeEach
    void init() {
        board = new Board();
    }

    @Test
    void defuseBomb() {
        // player uses 3 to defuse bomb
        assertEquals(0, board.attack('3', 'B'));
    }

    @Test
    void assassinateMarshal() {
        assertEquals(0, board.attack('1', 'M'));
    }


    @Test
    void stepOnBomb() {

        for (char i = 1; i <= 9; i ++) {
            char unit = (char)(i + '0');
            if (unit == '3')
                assertEquals(0, board.attack('3', 'B'));
            else
                assertEquals(1, board.attack(unit, 'B'));

        }

        assertEquals(1, board.attack('M', 'B'));
    }



    @Test
    void marshallAttack() {
        for (char i = 1; i <= 9; i ++) {
            char unit = (char)(i + '0');
            assertEquals(0, board.attack('M', unit));
        }

        assertEquals(1, board.attack('M', 'B'));
        assertEquals(3, board.attack('M', 'F'));
    }


    @Test
    void sameVal(){
        assertAll(
                () -> assertEquals(2, board.attack('1', '1')),
                () -> assertEquals(2, board.attack('2', '2')),
                () -> assertEquals(2, board.attack('3', '3')),
                () -> assertEquals(2, board.attack('4', '4')),
                () -> assertEquals(2, board.attack('5', '5')),
                () -> assertEquals(2, board.attack('6', '6')),
                () -> assertEquals(2, board.attack('7', '7')),
                () -> assertEquals(2, board.attack('8', '8')),
                () -> assertEquals(2, board.attack('9', '9')),
                () -> assertEquals(2, board.attack('M', 'M'))
        );
    }

    @Test
    void captureFlag() {
        for (char i = 1; i <= 9; i ++) {
            char unit = (char)(i + '0');
            assertEquals(3, board.attack(unit, 'F'));
        }
        assertSame(3, board.attack('M', 'F'));
    }

    @Test
    void orderKill() {

        for (int i = 1; i <= 9; i ++) {
            for (int j = 1; j <= 9; j ++) {
                char unit1 = (char)(i + '0');
                char unit2 = (char)(j + '0');

                if (unit1 == unit2) assertEquals(2, board.attack(unit1, unit2));
                if (unit1 > unit2) assertEquals(0, board.attack(unit1, unit2));
                if (unit1 < unit2) assertEquals(1, board.attack(unit1, unit2));
            }
        }
    }
}