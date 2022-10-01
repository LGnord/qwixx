package qwixx.arena;

import qwixx.execption.IllegalMoveException;

class IncreaseLine extends Line {

    IncreaseLine(Sheet sheet, Color color) {
        super(sheet, 0, color);
    }

    public IncreaseLine(Sheet sheet, int lastValue, Color color, boolean isClose, int nbValues) {
        super(sheet, lastValue, color, isClose, nbValues);
    }

    @Override
    void check(int value) throws IllegalMoveException {
        if (value <= lastValue) {
            throw new IllegalMoveException("Impossible to decreasing value: " + value + "<=" + lastValue);
        }
        if (value == 12 && nbValues <= 4) {
            throw new IllegalMoveException("Need more than 5 values to close a line. Current is " + nbValues);
        }
    }

    @Override
    void close(int value) {
        if (value == 12) {
            isClose = true;
            nbValues++;
            sheet.close(color);
        }
    }

    @Override
    public Line newLine() {
        return new IncreaseLine(sheet, lastValue, color, isClose, nbValues);
    }


}
