package qwixx.arena;


import qwixx.util.Random;

import java.util.List;


abstract class Dice {
    int value;

    Random random = new Random();

    public void rool() {
        value = random.integerRandom(6);
    }


    public abstract void combine(Dice other, List<Dices> diceList);

    protected abstract void combineWhite(WhiteDice whiteDice, List<Dices> diceList);

    protected void combineGreen(GreenDice greenDice, List<Dices> diceList) {
        // empty
    }

    protected void combineRed(RedDice redDice, List<Dices> diceList) {
        // empty
    }

    protected void combineYellow(YellowDice yellowDice, List<Dices> diceList) {
        // empty
    }

    protected void combineBlue(BlueDice blueDice, List<Dices> diceList) {
        // empty
    }
}

class WhiteDice extends Dice {

    @Override
    public void combine(Dice other, List<Dices> diceList) {
        other.combineWhite(this, diceList);
    }

    @Override
    protected void combineWhite(WhiteDice whiteDice, List<Dices> diceList) {
        for (Color color : Color.values()) {
            diceList.add(new Dices(color, value + whiteDice.value));
        }
    }

    @Override
    protected void combineBlue(BlueDice blueDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.BLUE, value + blueDice.value));
    }

    @Override
    protected void combineRed(RedDice redDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.BLUE, value + redDice.value));
    }

    @Override
    protected void combineYellow(YellowDice yellowDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.BLUE, value + yellowDice.value));
    }

    @Override
    protected void combineGreen(GreenDice greenDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.BLUE, value + greenDice.value));
    }
}

class RedDice extends Dice {

    @Override
    public void combine(Dice other, List<Dices> diceList) {
        other.combineRed(this, diceList);
    }

    @Override
    protected void combineWhite(WhiteDice whiteDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.RED, value + whiteDice.value));
    }

}

class YellowDice extends Dice {

    @Override
    public void combine(Dice other, List<Dices> diceList) {
        other.combineYellow(this, diceList);
    }

    @Override
    protected void combineWhite(WhiteDice whiteDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.YELLOW, value + whiteDice.value));
    }



}

class BlueDice extends Dice {

    @Override
    public void combine(Dice other, List<Dices> diceList) {
        other.combineBlue(this, diceList);
    }

    @Override
    protected void combineWhite(WhiteDice whiteDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.BLUE, value + whiteDice.value));
    }



}

class GreenDice extends Dice {

    @Override
    public void combine(Dice other, List<Dices> diceList) {
        other.combineGreen(this, diceList);
    }

    @Override
    protected void combineWhite(WhiteDice whiteDice, List<Dices> diceList) {
        diceList.add(new Dices(Color.GREEN, value + whiteDice.value));
    }


}