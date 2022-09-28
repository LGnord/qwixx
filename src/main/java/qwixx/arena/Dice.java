package qwixx.arena;

public class Dice {
    int value;

    public void rool() {
        value = (int)Math.ceil(Math.random()*6);
    }
}
