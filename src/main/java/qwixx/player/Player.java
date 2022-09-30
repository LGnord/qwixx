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
        return sheet.score() ;
    }

    public void show(AllDices allDices) {
        for (Dices dices : ml.bestDices(sheet, allDices)) {
            try {
                accept(dices);
            } catch (IllegalMoveException e) {
                ml.illegalMove(sheet, dices);
            }

        }
    }

    public void endGame() {

    }
}
