package qwixx.arena;

import qwixx.execption.IllegalMoveException;

import java.util.HashMap;
import java.util.Map;

public class Sheet {

    Map<Color, Line> lines;

    public Sheet() {
        lines = new HashMap<>();
        lines.put(Color.YELLOW, new IncreaseLine());
        lines.put(Color.RED, new IncreaseLine());
        lines.put(Color.BLUE, new DecreaseLine());
        lines.put(Color.GREEN, new DecreaseLine());

    }

    public void accept(Dices dices) throws IllegalMoveException {
        dices.update(this);
    }

    public void update(Color color, int value) throws IllegalMoveException{
        lines.get(color).update(value);
    }

    public int score() {
        int score = 0;
        for (Line line : lines.values()) {
            score += line.score();
        }
        return score;
    }
}
