package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.Config;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.io.UserAction;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Minesweeper implements GameInitializable, GameRunnable {

  private final Board board;
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;

  public Minesweeper(Config config) {
    board = new Board(config.getLevel());
    inputHandler = config.getInputHandler();
    outputHandler = config.getOutputHandler();
  }

  @Override
  public void initialize() {
    board.initializeGame();
  }

  @Override
  public void run() {
    outputHandler.showGameStartComments();

    while (board.isInProgress()) {
      try {
        outputHandler.showBoard(board);

        CellPosition cellInput = getCellInputFromUser();
        UserAction userAction = getUserActionInputFromUser();
        actOnCell(cellInput, userAction);

      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);
      } catch (Exception e) {
        outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
      }
    }

    outputHandler.showBoard(board);

    if (board.isWinStatus()) {
      outputHandler.showGameWinningComment();
    }
    if (board.isLoseStatus()) {
      outputHandler.showGameLosingComment();
    }
  }

  private void actOnCell(CellPosition cellPosition, UserAction userAction) {
    if (doesUserChooseToPlantFlag(userAction)) {
      board.flagAt(cellPosition);
      return;
    }

    if (doesUserChooseToOpenCell(userAction)) {
      board.openAt(cellPosition);
      return;
    }
    throw new GameException("잘못된 번호를 선택하셨습니다.");
  }

  private boolean doesUserChooseToOpenCell(UserAction userAction) {
    return userAction == UserAction.OPEN;
  }

  private boolean doesUserChooseToPlantFlag(UserAction userAction) {
    return userAction == UserAction.FLAG;
  }

  private UserAction getUserActionInputFromUser() {
    outputHandler.showUserActionComment();
    return inputHandler.getUserActionFromUser();
  }

  private CellPosition getCellInputFromUser() {
    outputHandler.showSelectingCellComment();
    CellPosition cellPosition = inputHandler.getCellPositionFromUser();

    if (board.isInvalidCellPosition(cellPosition)) {
      throw new GameException("잘못된 좌표를 선택하였습니다.");
    }

    return cellPosition;
  }
}
