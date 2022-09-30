package qwixx.arena;


import org.junit.jupiter.api.Test;

import qwixx.execption.IllegalMoveException;
import qwixx.player.Player;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    @Test
    public void ensure_increase_score_when_player_accept_dices_same_color() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 2);
        player.accept(dices);
        assertEquals(1, player.score());

        dices = new Dices(Color.RED, 3);
        player.accept(dices);
        assertEquals(3, player.score());

        dices = new Dices(Color.RED, 11);
        player.accept(dices);
        assertEquals(6, player.score());
    }

    @Test
    public void ensure_increase_score_when_player_accept_dices_different_color() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 2);
        player.accept(dices);
        assertEquals(1, player.score());

        dices = new Dices(Color.YELLOW, 3);
        player.accept(dices);
        assertEquals(2, player.score());

        dices = new Dices(Color.GREEN, 11);
        player.accept(dices);
        assertEquals(3, player.score());
    }

    @Test
    public void ensure_fail_decrease_dices() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 3);
        player.accept(dices);
        final Dices dices2 = new Dices(Color.RED, 2);
        assertThrows(IllegalMoveException.class, () -> player.accept(dices2));
    }

    @Test
    public void ensure_fail_close_without_5_values() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 3);
        player.accept(dices);
        final Dices dices2 = new Dices(Color.RED, 12);
        assertThrows(IllegalMoveException.class, () -> player.accept(dices2));
    }

    @Test
    public void ensure_close_increase_line() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        {
            Dices dices = new Dices(Color.GREEN, 12);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.GREEN, 11);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.GREEN, 10);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.GREEN, 9);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.GREEN, 8);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.GREEN, 2);
            player.accept(dices);
        }
        assertTrue(arena.closeLines.contains(Color.GREEN));
    }

    @Test
    public void ensure_close_decrease_line() throws IllegalMoveException {
        Arena arena = new Arena();
        Player player = new Player("2", arena);

        {
            Dices dices = new Dices(Color.RED, 3);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.RED, 4);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.RED, 5);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.RED, 6);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.RED, 7);
            player.accept(dices);
        }
        {
            Dices dices = new Dices(Color.RED, 12);
            player.accept(dices);
        }
        assertTrue(arena.closeLines.contains(Color.RED));
    }
}
