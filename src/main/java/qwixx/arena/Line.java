package qwixx.arena;

import qwixx.execption.IllegalMoveException;

public abstract class Line {

    int[] SCORE = {0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78};

    boolean isClose = false ;


    Line(int firstValue) {
        this.lastValue = firstValue;
    }

    int nbValues = 0;
    int lastValue;

     void update(int value) throws IllegalMoveException {
         if (isClose) {
             throw new IllegalMoveException("Line is close");
         }
        check(value);
        lastValue = value;
        nbValues++;
    }

     int score() {
        return SCORE[nbValues];
    }

    abstract void check(int value) throws IllegalMoveException;

    abstract void close(int value) throws IllegalMoveException;

}
