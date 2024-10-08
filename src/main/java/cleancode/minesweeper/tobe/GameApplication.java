package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.Config;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.level.Beginner;

public class GameApplication {

    public static void main(String[] args) {
      Config config = new Config(
          new Beginner(),
          new ConsoleInputHandler(),
        new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(config);
        minesweeper.initialize();
        minesweeper.run();
    }

}
