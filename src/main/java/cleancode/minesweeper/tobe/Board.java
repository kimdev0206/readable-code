package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.level.Level;

import java.util.Arrays;
import java.util.Random;

public class Board {

  private final Cell[][] board;
  private final int landMineCount;

  public Board(Level level) {
    int rowSize = level.getRowSize();
    int colSize = level.getColSize();
    board = new Cell[rowSize][colSize];

    landMineCount = level.getLandMineCount();
  }

  public void initializeGame() {
    for (int row = 0; row < getRowSize(); row++) {
      for (int col = 0; col < getColSize(); col++) {
        board[row][col] = Cell.create();
      }
    }

    for (int i = 0; i < landMineCount; i++) {
      int landMineRow = new Random().nextInt(getRowSize());
      int landMineCol = new Random().nextInt(getColSize());
      findCell(landMineRow, landMineCol).turnOnLandMine();
    }

    for (int row = 0; row < getRowSize(); row++) {
      for (int col = 0; col < getColSize(); col++) {
        if (isLandMineCell(row, col)) {
          continue;
        }
        int count = countNearbyLandMines(row, col);
        findCell(row, col).updateNearbyLandMineCount(count);
      }
    }
  }

  public void openSurroundedCells(int row, int col) {
    if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
      return;
    }
    if (isOpenedCell(row, col)) {
      return;
    }
    if (isLandMineCell(row, col)) {
      return;
    }

    open(row, col);

    if (doesCellHaveLandMineCount(row, col)) {
      return;
    }

    openSurroundedCells(row - 1, col - 1);
    openSurroundedCells(row - 1, col);
    openSurroundedCells(row - 1, col + 1);
    openSurroundedCells(row, col - 1);
    openSurroundedCells(row, col + 1);
    openSurroundedCells(row + 1, col - 1);
    openSurroundedCells(row + 1, col);
    openSurroundedCells(row + 1, col + 1);
  }

  public boolean isAllCellChecked() {
    return Arrays.stream(board)
      .flatMap(Arrays::stream)
      .allMatch(Cell::isChecked);
  }

  public void flag(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.flag();
  }

  public void open(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.open();
  }

  public boolean isLandMineCell(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.isLandMine();
  }

  public String getSign(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.getSign();
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  private boolean isOpenedCell(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.isOpened();
  }

  private boolean doesCellHaveLandMineCount(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.hasLandMineCount();
  }

  private Cell findCell(int row, int col) {
    return board[row][col];
  }

  private int countNearbyLandMines(int row, int col) {
    int count = 0;
    if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
      count++;
    }
    if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
      count++;
    }
    if (row - 1 >= 0 && col + 1 < getRowSize() && isLandMineCell(row - 1, col + 1)) {
      count++;
    }
    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
      count++;
    }
    if (col + 1 < getColSize() && isLandMineCell(row, col + 1)) {
      count++;
    }
    if (row + 1 < getRowSize() && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
      count++;
    }
    if (row + 1 < getRowSize() && isLandMineCell(row + 1, col)) {
      count++;
    }
    if (row + 1 < getRowSize() && col + 1 < getColSize() && isLandMineCell(row + 1, col + 1)) {
      count++;
    }
    return count;
  }

}
