package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.config.Config;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.minesweeper.level.Beginner;

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
