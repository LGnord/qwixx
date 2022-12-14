package qwixx.ia;

import org.junit.jupiter.api.Test;
import qwixx.arena.*;
import qwixx.execption.NoValidMoveException;
import qwixx.util.Random;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MLTest {

    @Test
    public void ensure_bestDices_return_dices_when_one_choice() throws NoValidMoveException {
        Random random = mock(Random.class);
        when(random.doubleRandom(anyDouble())).thenReturn(0.9);

        Arena arena = mock(Arena.class);
        AllDices allDices = mock(AllDices.class);

        Set<Dices> dices = new HashSet<>();
        dices.add(new Dices(Color.RED, 2));
        when(allDices.combine()).thenReturn(dices);
        ML ml = new ML(random);

        Collection<Dices> actual = ml.bestDices(new Sheet(arena), allDices.combine());
        assertEquals(1, actual.size());
        assertTrue(actual.containsAll(dices));
    }

    @Test
    public void ensure_bestDices_return_dices_when_no_valid_choice() throws NoValidMoveException {
        Random random = mock(Random.class);
        when(random.doubleRandom(anyDouble())).thenReturn(0.9);

        Arena arena = mock(Arena.class);
        AllDices allDices = mock(AllDices.class);

        Set<Dices> dices = new HashSet<>();
        dices.add(new Dices(Color.BLUE, 2));
        when(allDices.combine()).thenReturn(dices);
        ML ml = new ML(random);

        Collection<Dices> actual = ml.bestDices(new Sheet(arena), allDices.combine());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void ensure_bestDices_return_empty_when_no_choice() throws NoValidMoveException {
        Random random = mock(Random.class);
        when(random.doubleRandom(anyDouble())).thenReturn(0.9);

        Arena arena = mock(Arena.class);
        AllDices allDices = mock(AllDices.class);

        Set<Dices> dices = new HashSet<>();
        when(allDices.combine()).thenReturn(dices);
        ML ml = new ML(random);

        Collection<Dices> actual = ml.bestDices(new Sheet(arena), allDices.combine());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void ensure_bestDices_return_second_dice_when_random_1_2() throws NoValidMoveException {
        Random random = mock(Random.class);
        when(random.doubleRandom(anyDouble())).thenReturn(1.1);

        Arena arena = mock(Arena.class);
        AllDices allDices = mock(AllDices.class);

        Set<Dices> dices = new LinkedHashSet<>();
        dices.add(new Dices(Color.RED, 2));
        Dices expectedDices = new Dices(Color.BLUE, 3);
        dices.add(expectedDices);
        dices.add(new Dices(Color.RED, 3));
        when(allDices.combine()).thenReturn(dices);
        ML ml = new ML(random);

        Collection<Dices> actual = ml.bestDices(new Sheet(arena), allDices.combine());
        assertEquals(1, actual.size());
        assertTrue(actual.contains(expectedDices));
    }
}
