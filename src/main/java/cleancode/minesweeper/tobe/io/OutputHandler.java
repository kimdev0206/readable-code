package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Board;
import cleancode.minesweeper.tobe.GameException;

public interface OutputHandler {

  void showGameStartComments();

  void showBoard(Board board);

  void showGameWinningComment();

  void showGameLosingComment();

  void showSelectingCellComment();

  void showUserActionComment();

  void showExceptionMessage(GameException e);

  void showSimpleMessage(String message);

}
