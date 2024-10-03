package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.level.Beginner;
import cleancode.minesweeper.tobe.level.Level;

public class GameApplication {

    public static void main(String[] args) {
        Level level = new Beginner();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(level, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }

}
