package qwixx.arena;

import qwixx.execption.IllegalMoveException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Dices {

    Color color;
    int value;

    public Dices(Color color, int value) {
        assert 2 <= value && value <= 12;
        this.color = color;
        this.value = value;
    }

    public void update(Sheet sheet) throws IllegalMoveException {
        sheet.update(color,  value);
    }

    public static Set<Dices> allCombination () {
        Set<Dices> all = new HashSet<>();
        for (Color color : Color.values()) {
            for (int value = 2; value <= 12; value++) {
                all.add(new Dices(color, value));
            }
        }
        return all;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dices dices = (Dices) o;
        return value == dices.value && color == dices.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public String toString() {
        return "{" +
                "color=" + color +
                ", value=" + value +
                '}';
    }
}
