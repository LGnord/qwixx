package qwixx.arena;

import java.util.*;

public class AllDices {

    Dice[] whiteDices = {new WhiteDice(), new WhiteDice()};
    Dice[] coloredDices = {new YellowDice() , new RedDice(),
            new BlueDice(), new GreenDice()};

    void rool() {
        for(Dice dice : whiteDices) {
            dice.rool();
        }
        for(Dice dice : coloredDices) {
            dice.rool();
        }
    }

    public Set<Dices> combine() {
        List<Dices> diceList = new ArrayList<>();
        for (Dice d1 : whiteDices) {
            for (Dice d2 : coloredDices) {
                d1.combine(d2, diceList);
            }
        }
        return new HashSet<>(diceList);
    }

    @Override
    public String toString() {
        return "AllDices{" +
                "whiteDices=" + Arrays.toString(whiteDices) +
                ", coloredDices=" + Arrays.toString(coloredDices) +
                ", combine=" + combine() +
                '}';
    }
}
