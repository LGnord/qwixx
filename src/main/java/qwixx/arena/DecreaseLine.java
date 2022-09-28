package qwixx.arena;

import qwixx.execption.IllegalMoveException;

class DecreaseLine  extends Line {
    DecreaseLine() {
        super(13);
    }

    @Override
    void check(int value) throws IllegalMoveException {
        if (value >= lastValue) {
            throw new IllegalMoveException("Increasing value " + value+ ">=" + lastValue);
        }
        if (value == 2 && nbValues <=5) {
            throw new IllegalMoveException("Need more than 5 values to close a line. Current is" + nbValues);
        }
    }
}
