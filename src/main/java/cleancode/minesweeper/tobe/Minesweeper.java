package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

  private static final int BOARD_ROW_SIZE = 8;
  private static final int BOARD_COL_SIZE = 10;

  private final Board board = new Board(BOARD_ROW_SIZE, BOARD_COL_SIZE);
  private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
  private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

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
    int cellInputColIndex = getColIndexFrom(cellInput);
    int cellInputRowIndex = getRowIndexFrom(cellInput);

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

  private int getRowIndexFrom(String cellInput) {
    char cellInputRow = cellInput.charAt(1);
    return convertRowFrom(cellInputRow);
  }

  private int getColIndexFrom(String cellInput) {
    char cellInputCol = cellInput.charAt(0);
    return convertColFrom(cellInputCol);
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

  private int convertRowFrom(char cellInputRow) {
    int i = Character.getNumericValue(cellInputRow) - 1;

    if (i >= BOARD_ROW_SIZE) {
      throw new GameException("잘못된 입력입니다.");
    }

    return i;
  }

  private int convertColFrom(char cellInputCol) {
    switch (cellInputCol) {
      case 'a':
        return 0;
      case 'b':
        return 1;
      case 'c':
        return 2;
      case 'd':
        return 3;
      case 'e':
        return 4;
      case 'f':
        return 5;
      case 'g':
        return 6;
      case 'h':
        return 7;
      case 'i':
        return 8;
      case 'j':
        return 9;
      default:
        throw new GameException("잘못된 입력입니다.");
    }
  }

}
