package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.Board;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;

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
