package qwixx.player;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.AllDices;
import qwixx.arena.Arena;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.ia.ML;
import qwixx.util.Random;

import java.util.Set;

@Slf4j
public class Player {

    final Sheet sheet;
    final ML ml;
    final String id;
    Player leftPlayer;


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

    public void show(Set<Dices> combinedDices) {
        for (Dices dices : ml.bestDices(sheet, combinedDices)) {
            try {
                accept(dices);
            } catch (IllegalMoveException e) {
                ml.illegalMove(sheet, dices);
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
}
