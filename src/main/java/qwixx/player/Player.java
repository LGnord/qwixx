package qwixx.player;

import qwixx.arena.AllDices;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;

import java.util.HashMap;
import java.util.Map;

public class Player {

    private Sheet sheet = new Sheet();
    private final Map<Dices, Double> score;

    public Player() {
        this.score = new HashMap<>();
        for (Dices dices : Dices.allCombination()) {
            score.put(dices, 1d);
        }
    }

    public void accept(Dices dices) throws IllegalMoveException {
        sheet.accept(dices);
    }

    public int score() {
        return sheet.score();
    }

    public void show(AllDices allDices) throws IllegalMoveException {
        double sum = 0;
        for (Dices dices : allDices.combine()) {
            sum += score.get(dices);
        }
        double random = Math.random() * sum ;
        sum = 0;
        for (Dices dices : allDices.combine()) {
            sum += score.get(dices);
            if (sum > random) {
                accept(dices);
                return;
            }
        }
    }
}
