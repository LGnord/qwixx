package qwixx.ia;

import qwixx.arena.AllDices;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.execption.NoValidMoveException;

import java.util.*;

public class ML {

    final Map<Sheet, Map<Dices, Double>> states;

    public ML() {
        this.states = new HashMap<>();
    }

    public Collection<Dices> bestDices(Sheet sheet, AllDices allDices) throws NoValidMoveException {
        List<Dices> best = new ArrayList<>();
        Map<Dices, Double> score = states.getOrDefault(sheet, emptyScore());
        states.put(sheet, score);
        double sum = 0;
        for (Dices dices : allDices.combine()) {
            sum += score.get(dices);
        }
        if (sum == 0) {
            throw new NoValidMoveException();
        }
        double random = Math.random() * sum ;
        sum = 0;
        for (Dices dices : allDices.combine()) {
            sum += score.get(dices);
            if (sum > random) {
                return best;
            }
        }
        return best;
    }

    private Map<Dices, Double> emptyScore() {
        Map<Dices, Double> map = new HashMap<>();
        for (Dices dices : Dices.allCombination()) {
            map.put(dices, 1d);
        }
        return map;
    }

    public void illegalMove(Sheet sheet, Dices dices) {
    }
}
