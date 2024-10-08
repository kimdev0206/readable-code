package cleancode.minesweeper.tobe.config;

import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.level.Level;

public class Config {

  private final Level level;
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;

  public Config(Level level, InputHandler inputHandler, OutputHandler outputHandler) {
    this.level = level;
    this.inputHandler = inputHandler;
    this.outputHandler = outputHandler;
  }

  public Level getLevel() {
    return level;
  }

  public InputHandler getInputHandler() {
    return inputHandler;
  }

  public OutputHandler getOutputHandler() {
    return outputHandler;
  }
}
