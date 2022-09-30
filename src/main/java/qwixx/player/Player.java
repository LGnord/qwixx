package qwixx.player;

import qwixx.arena.AllDices;
import qwixx.arena.Arena;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.execption.NoValidMoveException;
import qwixx.ia.ML;

import java.util.HashMap;
import java.util.Map;

public class Player {

    final Sheet sheet ;
    final ML ml;


    public Player(Arena arena) {
        this.sheet = new Sheet(arena);
        this.ml = new ML();


    }

    public void accept(Dices dices) throws IllegalMoveException {
        sheet.accept(dices);
    }

    public int score() {
        return sheet.score();
    }

    public void show(AllDices allDices) throws NoValidMoveException {
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
