package qwixx.ia;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
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

    public Collection<Dices> bestDices(Sheet sheet, Set<Dices> combinedDices) {
        List<Dices> bestList = new ArrayList<>();
        Map<Dices, Double> score = states.getOrDefault(sheet, emptyScore(sheet));
        states.put(sheet, score);
        List<Dices> combine = new ArrayList<>(combinedDices);
        double[] intervals = new double[combine.size() + 1];
        intervals[0] = 0d;
        for (int i = 0; i < combine.size(); i++) {
            intervals[i + 1] = intervals[i] + score.getOrDefault(combine.get(i), 0d);
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

    private Map<Dices, Double> emptyScore(Sheet sheet) {
        Map<Dices, Double> map = new HashMap<>();
        for (Dices dices : Dices.allCombination()) {
            try {
                sheet.check(dices);
                map.put(dices, 1d);
            } catch (IllegalMoveException e) {
              // empty
            }
        }
        return map;
    }


}
