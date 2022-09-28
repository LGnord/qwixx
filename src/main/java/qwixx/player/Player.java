package qwixx.player;

import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;

public class Player {

    private Sheet sheet = new Sheet();

    public void accept(Dices dices) throws IllegalMoveException {
        sheet.accept(dices);
    }

    public int score() {
        return sheet.score();
    }
}
