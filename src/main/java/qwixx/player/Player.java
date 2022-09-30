package qwixx.player;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.AllDices;
import qwixx.arena.Arena;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.ia.ML;
import qwixx.util.Random;

@Slf4j
public class Player {

    final Sheet sheet;
    final ML ml;
    final String id;
    Player leftPlayer;
    boolean isCurrentPlayer = false;


    public Player(String id, Arena arena) {
        this.sheet = new Sheet(arena);
        this.ml = new ML(new Random());
        this.id = id;
        arena.register(this);
    }

    public void accept(Dices dices) throws IllegalMoveException {
        log.debug("{}: plays {}. Sheet: {}", id, dices, sheet);
        sheet.accept(dices);
    }

    public int score() {
        return sheet.score();
    }

    public void show(AllDices combinedDices) {
        boolean mustPlay = true;
        for (Dices dices : ml.bestDices(sheet, combinedDices.combinePublic())) {
            try {
                accept(dices);
                mustPlay = false;
            } catch (IllegalMoveException e) {
               // empty
            }
        }
        if (isCurrentPlayer) {
            log.info("{} is the current player (must play : {}) : {} ", id, mustPlay, sheet);
            for (Dices dices : ml.bestDices(sheet, combinedDices.combine())) {
                try {
                    accept(dices);
                } catch (IllegalMoveException e) {
                    if (mustPlay) {
                        log.debug("Increase malus due to: '{}'", e.getMessage());
                        sheet.malus();
                    }
                }
            }
        }
    }

    public void endGame() {
        // empty
    }

    public Player leftPlayer() {
        return leftPlayer;
    }

    public void leftPlayer(Player currentPlayer) {
        this.leftPlayer = currentPlayer;
    }

    public void becomeFirstPlayer() {
        isCurrentPlayer = true;
    }

    public void becomeLastPlayer() {
        isCurrentPlayer = false;
    }
}
