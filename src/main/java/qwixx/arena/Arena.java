package qwixx.arena;

import qwixx.execption.IllegalMoveException;
import qwixx.player.Player;

import java.util.List;


public class Arena {

    AllDices dices = new AllDices();
    List<Player> players;

    void round() throws IllegalMoveException {
        dices.rool();
        for (Player player : players) {
            player.show(dices);
        }
    }

}
