package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.level.Level;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

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
    CellPositions cellPositions = CellPositions.from(board);
    initializeEmptyCells(cellPositions);

    List<CellPosition> landMinePositionList = cellPositions.extractRandomPositions(landMineCount);
    initializeLandMineCells(landMinePositionList);

    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositionList);
    initializeNumberCells(numberPositionCandidates);
  }

  private void initializeEmptyCells(CellPositions cellPositions) {
    List<CellPosition> positionList = cellPositions.getPositionList();
    for (CellPosition position : positionList) {
      updateCellAt(position, new EmptyCell());
    }
  }

  private void initializeLandMineCells(List<CellPosition> landMinePositionList) {
    for (CellPosition position : landMinePositionList) {
      updateCellAt(position, new LandMineCell());
    }
  }

  private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
    for (CellPosition positionCandidate : numberPositionCandidates) {
      int count = countNearbyLandMines(positionCandidate);
      if (count != 0) {
        updateCellAt(positionCandidate, new NumberCell(count));

      }
    }
  }

  private void updateCellAt(CellPosition position, Cell cell) {
    board[position.getRowIndex()][position.getColIndex()] = cell;
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
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
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

  public CellSnapshot getSnapshot(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.getSnapshot();
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
