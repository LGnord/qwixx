package qwixx.util;

public class Random {


    public int integerRandom(int maxValue) {
        return (int) Math.ceil(Math.random() * maxValue);
    }

    public double doubleRandom(double maxValue) {
        return Math.ceil(Math.random() * maxValue);
    }
}
