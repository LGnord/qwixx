package qwixx.ia;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.AllDices;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.util.Random;

import java.util.*;

@Slf4j
public class ML {

    final Map<Sheet, Map<Dices, Double>> states;
    final Random random;

    public ML(Random random) {
        this.random = random;
        this.states = new HashMap<>();
    }

    public Collection<Dices> bestDices(Sheet sheet, AllDices allDices) {
        List<Dices> bestList = new ArrayList<>();
        Map<Dices, Double> score = states.getOrDefault(sheet, emptyScore());
        states.put(sheet, score);
        List<Dices> combine = new ArrayList<>(allDices.combine());
        double[] intervals = new double[combine.size() + 1];
        intervals[0] = 0d;
        for (int i = 0; i < combine.size(); i++) {
            intervals[i + 1] = intervals[i] + score.get(combine.get(i));
        }

        double random = this.random.doubleRandom(intervals[intervals.length - 1]);

        for (int i = 0; i < combine.size(); i++) {
            if (intervals[i] <= random && random < intervals[i + 1]) {
                bestList.add(combine.get(i));
            }
        }
        log.debug("ML recommends to play {} because {} between {} in {}.", bestList, random, intervals, combine);
        return bestList;
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
