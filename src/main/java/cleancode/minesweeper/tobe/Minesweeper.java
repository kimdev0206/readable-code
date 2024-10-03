package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.level.Level;

public class Minesweeper implements GameInitializable, GameRunnable {

  private final Board board;
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
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

        String cellInput = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(cellInput, userActionInput);

      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);
      } catch (Exception e) {
        outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
      }
    }
  }

  private void actOnCell(String cellInput, String userActionInput) {
    int cellInputColIndex = boardIndexConverter.getColIndexFrom(cellInput, board.getColSize());
    int cellInputRowIndex = boardIndexConverter.getRowIndexFrom(cellInput, board.getRowSize());

    if (doesUserChooseToPlantFlag(userActionInput)) {
      board.flag(cellInputRowIndex, cellInputColIndex);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userActionInput)) {
      if (board.isLandMineCell(cellInputRowIndex, cellInputColIndex)) {
        board.open(cellInputRowIndex, cellInputColIndex);
        changeGameStatusToLose();
        return;
      }

      board.openSurroundedCells(cellInputRowIndex, cellInputColIndex);
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

  private boolean doesUserChooseToOpenCell(String userActionInput) {
    return userActionInput.equals("1");
  }

  private boolean doesUserChooseToPlantFlag(String userActionInput) {
    return userActionInput.equals("2");
  }

  private String getUserActionInputFromUser() {
    outputHandler.showUserActionComment();
    return inputHandler.getUserInput();
  }

  private String getCellInputFromUser() {
    outputHandler.showSelectingCellComment();
    return inputHandler.getUserInput();
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
