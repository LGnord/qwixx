package qwixx.arena;

class Dice {
    int value;

    public void rool() {
        value = (int)Math.ceil(Math.random()*6);
    }
}
