package qwixx.player;

import lombok.extern.slf4j.Slf4j;
import qwixx.arena.AllDices;
import qwixx.arena.Arena;
import qwixx.arena.Dices;
import qwixx.arena.Sheet;
import qwixx.execption.IllegalMoveException;
import qwixx.ia.ML;
import qwixx.util.Random;

import java.util.Collection;

@Slf4j
public class Player {

    Sheet sheet;
    final ML ml;
    final String id;
    Player leftPlayer;
    boolean isCurrentPlayer = false;


    public Player(String id, Arena arena) {
        this.sheet = new Sheet(arena);
        this.ml = ML.getInstance(new Random());
        this.id = id;
        arena.register(this);
    }

    public void accept(Dices dices)  {
        log.debug("{} plays {}. Sheet: {}", id, dices, sheet);
        ml.learn(this, sheet, dices);
        sheet = sheet.accept(dices);
    }

    public int score() {
        return sheet.score();
    }

    public void newGame() {
        ml.newGame();
    }

    public void show(AllDices combinedDices) {
        Collection<Dices> publicDices = ml.bestDices(sheet, combinedDices.combinePublic());
        boolean mustPlay = publicDices.isEmpty();
        for (Dices dices : publicDices) {
            accept(dices);
        }
        if (isCurrentPlayer) {
            log.info("{} is the current player (must play : {}) : {} ", id, mustPlay, sheet);
            Collection<Dices> privateDices = ml.bestDices(sheet, combinedDices.combine());
            for (Dices dices : privateDices) {
                accept(dices);
            }
            if (mustPlay && privateDices.isEmpty()) {
                log.debug("Increase malus because ML recommands to not play");
                sheet = sheet.malus();
            }
        }
    }

    public void endGame(int rank) {
        log.info("{} is ranked {} with a score {}.", id, rank, score());
        if (rank == 1) {
            ml.win(this);
        } else {
            ml.lost(this);
        }
    }

    public Player leftPlayer() {
        return leftPlayer;
    }

    public void leftPlayer(Player currentPlayer) {
        this.leftPlayer = currentPlayer;
    }

    public void becomeFirstPlayer() {
        isCurrentPlayer = true;
    }

    public void becomeLastPlayer() {
        isCurrentPlayer = false;
    }
}
