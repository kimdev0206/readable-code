package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.io.UserAction;
import cleancode.minesweeper.tobe.level.Level;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Minesweeper implements GameInitializable, GameRunnable {

  private final Board board;
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(Level level, InputHandler inputHandler, OutputHandler outputHandler) {
    board = new Board(level);
    this.inputHandler = inputHandler;
    this.outputHandler = outputHandler;
  }

  @Override
  public void initialize() {
    board.initializeGame();
  }

  @Override
  public void run() {
    outputHandler.showGameStartComments();

    while (true) {
      try {
        outputHandler.showBoard(board);

        if (doesUserWinTheGame()) {
          outputHandler.showGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          outputHandler.showGameLosingComment();
          break;
        }

        CellPosition cellInput = getCellInputFromUser();
        UserAction userAction = getUserActionInputFromUser();
        actOnCell(cellInput, userAction);

      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);
      } catch (Exception e) {
        outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
      }
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
    gameStatus = 1;
  }

  private void changeGameStatusToLose() {
    gameStatus = -1;
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
    return gameStatus == -1;
  }

  private boolean doesUserWinTheGame() {
    return gameStatus == 1;
  }

  private void checkIfGameIsOver() {
    if (board.isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

}
