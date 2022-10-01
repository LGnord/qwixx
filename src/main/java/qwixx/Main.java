package qwixx;

import qwixx.arena.Arena;
import qwixx.player.Player;

public class Main {

    public  static void  main(String[] args) {
        Arena arena = new Arena();
        new Player("P1", arena);
        new Player("P2", arena);
        arena.playQwixx();
    }
}
