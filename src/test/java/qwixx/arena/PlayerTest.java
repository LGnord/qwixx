package qwixx.arena;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import qwixx.execption.IllegalMoveException;
import qwixx.player.Player;


public class PlayerTest {

    @Test
    public void ensure_increase_score_when_player_accept_dices_same_color() throws IllegalMoveException {
        Player player = new Player();

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
        Player player = new Player();

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
        Player player = new Player();

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 3);
        player.accept(dices);
        final Dices dices2 = new Dices(Color.RED, 2);
        assertThrows(IllegalMoveException.class, () -> player.accept(dices2));
    }

    @Test
    public void ensure_fail_close_without_5_vallues() throws IllegalMoveException {
        Player player = new Player();

        assertEquals(0, player.score());
        Dices dices = new Dices(Color.RED, 3);
        player.accept(dices);
        final Dices dices2 = new Dices(Color.RED, 12);
        assertThrows(IllegalMoveException.class, () -> player.accept(dices2));
    }
}
