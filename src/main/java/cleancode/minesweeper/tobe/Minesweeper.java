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
  private GameStatus status;

  public Minesweeper(Config config) {
    board = new Board(config.getLevel());
    inputHandler = config.getInputHandler();
    outputHandler = config.getOutputHandler();
    status = GameStatus.IN_PROGRESS;
  }

  @Override
  public void initialize() {
    board.initializeGame();
    status = GameStatus.IN_PROGRESS;
  }

  @Override
  public void run() {
    outputHandler.showGameStartComments();

    while (status == GameStatus.IN_PROGRESS) {
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

    if (doesUserWinTheGame()) {
      outputHandler.showGameWinningComment();
    }
    if (doesUserLoseTheGame()) {
      outputHandler.showGameLosingComment();
    }
  }

  private void actOnCell(CellPosition cellPosition, UserAction userAction) {
    if (doesUserChooseToPlantFlag(userAction)) {
      board.flagAt(cellPosition);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userAction)) {
      if (board.isLandMineCellAt(cellPosition)) {
        board.openAt(cellPosition);
        changeGameStatusToLose();
        return;
      }

      board.openSurroundedCells(cellPosition);
      checkIfGameIsOver();
      return;
    }
    System.out.println("잘못된 번호를 선택하셨습니다.");
  }

  private void changeGameStatusToWin() {
    status = GameStatus.WIN;
  }

  private void changeGameStatusToLose() {
    status = GameStatus.LOSE;
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

  private boolean doesUserLoseTheGame() {
    return status == GameStatus.LOSE;
  }

  private boolean doesUserWinTheGame() {
    return status == GameStatus.WIN;
  }

  private void checkIfGameIsOver() {
    if (board.isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

}
