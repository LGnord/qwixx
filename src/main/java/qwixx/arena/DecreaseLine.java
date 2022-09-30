package qwixx.arena;

import qwixx.execption.IllegalMoveException;

class DecreaseLine  extends Line {
    DecreaseLine(Sheet sheet) {
        super(sheet, 13);
    }

    @Override
    void check(int value) throws IllegalMoveException {
        if (value >= lastValue) {
            throw new IllegalMoveException("Impossible to increasing value: " + value+ ">=" + lastValue);
        }
        if (value == 2 && nbValues <=4) {
            throw new IllegalMoveException("Need more than 5 values to close a line. Current is " + nbValues);
        }
    }

    @Override
    void close(int value) throws IllegalMoveException {
        if (value == 2) {
            isClose = true;
            nbValues++;
            sheet.close(this);
        }
    }
}
