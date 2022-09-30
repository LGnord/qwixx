package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import qwixx.execption.IllegalMoveException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Sheet {

    Map<Color, Line> lines;
    Set<Line> closeLines;
    final Arena arena;
    int nbMalus = 0;

    public Sheet(Arena arena) {
        this.arena = arena;
        lines = new HashMap<>();
        lines.put(Color.YELLOW, new IncreaseLine(this));
        lines.put(Color.RED, new IncreaseLine(this));
        lines.put(Color.BLUE, new DecreaseLine(this));
        lines.put(Color.GREEN, new DecreaseLine(this));
        closeLines = new HashSet<>();
    }

    public void accept(Dices dices) {
        try {
            dices.update(this);
        } catch (IllegalMoveException e) {
            log.debug("Increase malus due to {}", e.getMessage());
            malus();
        }
    }

    private  void malus() {
        nbMalus++;
        if (nbMalus >= 4) {
            arena.fourthMalus();
        }
    }

    public void update(Color color, int value) throws IllegalMoveException {
        lines.get(color).update(value);
    }

    public int score() {
        int score = 0;
        for (Line line : lines.values()) {
            score += line.score();
        }
        return score - 5* nbMalus;
    }

    public void close(Line line) {
        closeLines.add(line);
        for (Color color : lines.keySet()) {
            if (line == lines.get(color)) {
                arena.closeLine(color);
            }
        }
    }

    @Override
    public String toString() {
        return "Sheet{" +
                "lines=" + lines +
                ", malus=" + nbMalus +
                '}';
    }
}
