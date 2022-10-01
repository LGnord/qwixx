package qwixx.ia;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.player.Player;
import qwixx.util.Random;

import java.util.*;

@Slf4j
public class ML {

    final Map<Sheet, Map<Dices, Double>> states;
    final Random random;

    static ML INSTANCE;

    public static ML getInstance(Random random) {
        if (INSTANCE == null) {
            INSTANCE = new ML(random);
        }
        return INSTANCE;
    }

    ML(Random random) {
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

    Map<Player, List<State>> history;

    public void learn(Player p, Sheet sheet, Dices played) {
        List<State> stateList = history.getOrDefault(p, new ArrayList<>());
        stateList.add(new State(sheet, played.copy()));
        history.put(p, stateList);
    }

    public void newGame() {
        history = new HashMap<>();
    }

    public void win(Player player) {
        reward(1, history.get(player));
    }

    public void lost(Player player) {
        reward(-1, history.get(player));
    }

    static double REWARD_RATE = 0.9;

    private void reward(int coeff,  List<State> stateList) {
        log.debug("Reward {}, {}", coeff, stateList);
        double reward = coeff;
        for (int i = stateList.size() - 1; i >= 0; i--) {
            State state = stateList.get(i);
            Map<Dices, Double> proba = states.get(state.sheet);
            proba.put(state.played, proba.get(state.played) + reward);
            reward = reward * REWARD_RATE;
        }
        log.debug("{}", states);
    }


}

class State {
    final Sheet sheet;
    final Dices played;

    State(Sheet sheet, Dices played) {
        this.sheet = sheet;
        this.played = played;
    }

    @Override
    public String toString() {
        return "*State{" +
                "sheet=" + sheet +
                ", played=" + played +
                "}*";
    }
}