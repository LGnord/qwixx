package qwixx.arena;

import qwixx.execption.NoValidMoveException;
import qwixx.player.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Arena {

    AllDices dices = new AllDices();
    List<Player> players;

    Set<Color> closeLines = new HashSet<>();

    void round() throws  NoValidMoveException {
        dices.rool();
        for (Player player : players) {
            player.show(dices);
        }
    }

    public void closeLine(Color color) {
        closeLines.add(color);
    }
}
