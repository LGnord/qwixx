package qwixx.arena;

import qwixx.execption.IllegalMoveException;

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
}
