package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.level.Level;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
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
        board[row][col] = new EmptyCell();
      }
    }

    for (int i = 0; i < landMineCount; i++) {
      int landMineRow = new Random().nextInt(getRowSize());
      int landMineCol = new Random().nextInt(getColSize());
      board[landMineRow][landMineCol] = new LandMineCell();
    }

    for (int row = 0; row < getRowSize(); row++) {
      for (int col = 0; col < getColSize(); col++) {
        CellPosition cellPosition = CellPosition.of(row, col);

        if (isLandMineCellAt(cellPosition)) {
          continue;
        }
        int count = countNearbyLandMines(cellPosition);
        if (count == 0) {
          continue;
        }
        board[row][col] = new NumberCell(count);
      }
    }
  }

  public void openSurroundedCells(CellPosition cellPosition) {
    if (isOpenedCell(cellPosition)) {
      return;
    }
    if (isLandMineCellAt(cellPosition)) {
      return;
    }

    openAt(cellPosition);

    if (doesCellHaveLandMineCount(cellPosition)) {
      return;
    }

    List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition);
    surroundedPositions.forEach(this::openSurroundedCells);
  }

  public boolean isAllCellChecked() {
    return Arrays.stream(board)
      .flatMap(Arrays::stream)
      .allMatch(Cell::isChecked);
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();
  }

  public void openAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  public boolean isLandMineCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isLandMine();
  }

  public String getSign(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.getSign();
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
      || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.hasLandMineCount();
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }

  private int countNearbyLandMines(CellPosition cellPosition) {
    long count = calculateSurroundedPositions(cellPosition)
      .stream()
      .filter(this::isLandMineCellAt)
      .count();

    return (int) count;
  }

  private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
    return RelativePosition.SURROUNDED_POSITIONS.stream()
      .filter(cellPosition::canCalculatePositionBy)
      .map(cellPosition::calculatePositionBy)
      .filter(position -> position.isRowIndexLessThan(getRowSize()))
      .filter(position -> position.isColIndexLessThan(getColSize()))
      .toList();
  }
}
