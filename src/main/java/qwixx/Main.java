package qwixx;

import qwixx.arena.Arena;
import qwixx.player.Player;

public class Main {

    public  static void  main(String[] args) {
        Arena arena = new Arena();
        new Player("1", arena);
        new Player("2", arena);
        arena.playQwixx();
    }
}
