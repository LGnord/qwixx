package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import qwixx.execption.IllegalMoveException;

import java.util.*;

@Slf4j
public class Sheet {

    Map<Color, Line> lines;
    Set<Color> closeLines;
    final Arena arena;
    final int nbMalus;

    private Sheet(Arena arena, Map<Color, Line> lines, Set<Color> closeLines, int nbMalus) {
        this.arena = arena;
        this.nbMalus = nbMalus;
        this.lines = new HashMap<>();
        this.closeLines = new HashSet<>();
        for (Color color : lines.keySet()) {
            this.lines.put(color, lines.get(color).newLine());
        }
        for (Color line : closeLines) {
            this.closeLines.add(line);
        }
    }

    public Sheet(Arena arena) {
        this.nbMalus = 0;
        this.arena = arena;
        lines = new HashMap<>();
        lines.put(Color.YELLOW, new IncreaseLine(this, Color.YELLOW));
        lines.put(Color.RED, new IncreaseLine(this, Color.RED));
        lines.put(Color.BLUE, new DecreaseLine(this, Color.BLUE));
        lines.put(Color.GREEN, new DecreaseLine(this, Color.GREEN));
        closeLines = new HashSet<>();
    }

    public Sheet accept(Dices dices) {
        return dices.update(this);
    }

    public void check(Dices dices) throws IllegalMoveException {
        lines.get(dices.color).check(dices.value);
    }

    public Sheet malus() {
        Sheet updated = new Sheet(arena, lines, closeLines, nbMalus + 1);
        if (updated.nbMalus >= 4) {
            arena.fourthMalus();
        }
        return updated;
    }

    public Sheet update(Color color, int value) {
        Sheet updated = new Sheet(arena, lines, closeLines, nbMalus);
        try {
            updated.lines.get(color).update(value);
        } catch (IllegalMoveException e) {
            throw new IllegalStateException("Should never play illegal move.", e);
        }
        return updated;
    }

    public int score() {
        int score = 0;
        for (Line line : lines.values()) {
            score += line.score();
        }
        return score - 5 * nbMalus;
    }

    public void close(Color color) {
        closeLines.add(color);
        arena.closeLine(color);

    }

    @Override
    public String toString() {
        return "Sheet{" +
                "lines=" + lines +
                ", malus=" + nbMalus +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sheet)) return false;
        Sheet sheet = (Sheet) o;
        return nbMalus == sheet.nbMalus && Objects.equals(lines, sheet.lines) && Objects.equals(closeLines, sheet.closeLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines, closeLines, nbMalus);
    }
}
