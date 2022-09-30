package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
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
    boolean isFourthMalus = false;

    void round(Player currentPlayer) {
        log.debug("--- New round ---");
        dices.rool();
        log.info("dices : {}", dices);

        for (Player player : players) {
            player.show(dices);
        }


        if (closeLines.size() > +2) {
            for (Player player : players) {
                player.endGame();
            }
        }
    }

    public void closeLine(Color color) {
        log.debug("Close line {}. Lines already closed", color, closeLines);
        closeLines.add(color);
    }

    public void register(Player player) {
        log.info("New player.");
        players.add(player);
    }

    public void playQwixx() {
        Player currentPlayer = players.get(0);
        for (Player player : players) {
            player.leftPlayer(currentPlayer);
            currentPlayer = player;
        }
        currentPlayer.leftPlayer(players.get(0));
        currentPlayer.becomeFirstPlayer();
        while (continueToPlay()) {
            round(currentPlayer);
            currentPlayer.becomeLastPlayer();
            currentPlayer = currentPlayer.leftPlayer();
            currentPlayer.becomeFirstPlayer();
        }
        for (Player player : players) {
            log.info("Final score {}", player.score());
        }
    }

    boolean continueToPlay() {
        return !isFourthMalus && closeLines.size() < 2;
    }

    public void fourthMalus() {
        log.debug("End game : more than four malus.");
        isFourthMalus = true;
    }
}
