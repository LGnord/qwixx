package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DiceTest {

    @Test
    public void ensure_rool_return_1_6() {
        Dice dice = new GreenDice();
        dice.rool();
        log.info("Dice : {}", dice.value);
        assert(dice.value >= 1);
        assert(dice.value <= 6);
    }
}
