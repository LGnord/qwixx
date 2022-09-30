package qwixx.arena;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class AllDicesTest {

    @Test
    public void ensure_number_of_dices_per_color() {
        AllDices allDices = new AllDices();
        allDices.rool();
        Set<Dices> combine = allDices.combine();
        log.info("{}", combine);
        for (Color color : Color.values()) {
            assertContainsOneOrTwoOccurrence(color, combine);
        }

    }

    void assertContainsOneOrTwoOccurrence(Color color, Set<Dices> dices) {
        int nbOccurrences = 0;
        for (Dices dices1 : dices) {
            if (dices1.color == color) {
                nbOccurrences ++;
            }
        }
        assertTrue(nbOccurrences>0);
        assertTrue(nbOccurrences<=2);
    }
}
