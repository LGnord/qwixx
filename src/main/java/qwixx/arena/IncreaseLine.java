package qwixx.arena;

import qwixx.execption.IllegalMoveException;

class IncreaseLine extends Line {
    IncreaseLine() {
        super(0);
    }

    @Override
    void check(int value) throws IllegalMoveException {
        if (value <= lastValue) {
            throw new IllegalMoveException("Decreasing value " + value+ "<=" + lastValue);
        }
        if (value == 12 && nbValues <=5) {
            throw new IllegalMoveException("Need more than 5 values to close a line. Current is" + nbValues);
        }
    }
}
