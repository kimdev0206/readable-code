package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.level.Level;

public class Minesweeper {

  private final Board board;
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
  private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
  private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(Level level) {
    board = new Board(level);
  }

  public void run() {
    consoleOutputHandler.showGameStartComments();
    board.initializeGame();

    while (true) {
      try {
        consoleOutputHandler.showBoard(board);

        if (doesUserWinTheGame()) {
          consoleOutputHandler.printGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          consoleOutputHandler.printGameLosingComment();
          break;
        }

        String cellInput = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(cellInput, userActionInput);

      } catch (GameException e) {
        consoleOutputHandler.printExceptionMessage(e);
      } catch (Exception e) {
        consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
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
    consoleOutputHandler.printUserActionComment();
    return consoleInputHandler.getUserInput();
  }

  private String getCellInputFromUser() {
    consoleOutputHandler.printSelectingCellComment();
    return consoleInputHandler.getUserInput();
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
