package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.level.Beginner;
import cleancode.minesweeper.tobe.level.Level;

public class GameApplication {

    public static void main(String[] args) {
        Level level = new Beginner();

        Minesweeper minesweeper = new Minesweeper(level);
        minesweeper.initialize();
        minesweeper.run();
    }

}
