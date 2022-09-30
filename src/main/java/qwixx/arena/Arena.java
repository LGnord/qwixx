package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import qwixx.execption.NoValidMoveException;
import qwixx.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public class Arena {

    AllDices dices = new AllDices();
    List<Player> players = new ArrayList<>();

    Set<Color> closeLines = new HashSet<>();

    void round() {
        dices.rool();
        log.info("dices : {}", dices);
        for (Player player : players) {
            player.show(dices);
        }
        if (closeLines.size() >+ 2) {
            for (Player player : players) {
                player.endGame();
            }
        }
    }

    public void closeLine(Color color) {
        closeLines.add(color);
    }

    public void register(Player player) {
        log.info("New player.");
        players.add(player);
    }

    public void playQwixx() {
        while (closeLines.size() < 2 ) {
            round();
        }
    }
}
