package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import qwixx.player.Player;

import java.util.*;
import java.util.stream.Collectors;


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
        Map<Integer, List<Player>> scores = new HashMap<>();
        for (Player player : players) {
            List<Player> p = scores.getOrDefault(player.score(), new ArrayList<>());
            p.add(player);
            scores.put(player.score(), p);
        }
        List<Integer> ranking = scores.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (int rank = 0; rank < ranking.size(); rank++) {
            for (Player player : scores.get(ranking.get(rank))) {
                player.endGame(rank + 1);
            }
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
