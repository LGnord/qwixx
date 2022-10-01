package qwixx.arena;

import qwixx.execption.IllegalMoveException;

class DecreaseLine extends Line {


    DecreaseLine(Sheet sheet, Color color) {
        super(sheet, 13, color);
    }

    public DecreaseLine(Sheet sheet, int lastValue, Color color, boolean isClose, int nbValues) {
        super(sheet, lastValue, color, isClose, nbValues);
    }

    @Override
    void check(int value) throws IllegalMoveException {
        if (value >= lastValue) {
            throw new IllegalMoveException("Impossible to increasing value: " + value + ">=" + lastValue);
        }
        if (value == 2 && nbValues <= 4) {
            throw new IllegalMoveException("Need more than 5 values to close a line. Current is " + nbValues);
        }
    }

    @Override
    void close(int value) throws IllegalMoveException {
        if (value == 2) {
            isClose = true;
            nbValues++;
            sheet.close(color);
        }
    }

    @Override
    public Line newLine() {
        return new DecreaseLine(sheet, lastValue, color, isClose, nbValues);
    }
}
