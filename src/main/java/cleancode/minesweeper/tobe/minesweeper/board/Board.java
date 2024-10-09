package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.level.Level;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;

import java.util.List;

public class Board {

  private final Cell[][] board;
  private final int landMineCount;

  private GameStatus gameStatus;

  public Board(Level level) {
    int rowSize = level.getRowSize();
    int colSize = level.getColSize();
    board = new Cell[rowSize][colSize];

    landMineCount = level.getLandMineCount();
    initializeGameStatus();
  }

  // 상태 변경 //
  public void initializeGame() {
    initializeGameStatus();

    CellPositions cellPositions = CellPositions.from(board);
    initializeEmptyCells(cellPositions);

    List<CellPosition> landMinePositionList = cellPositions.extractRandomPositions(landMineCount);
    initializeLandMineCells(landMinePositionList);

    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositionList);
    initializeNumberCells(numberPositionCandidates);
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();

    checkIfGameIsOver();
  }

  public void openAt(CellPosition cellPosition) {
    if (isLandMineCellAt(cellPosition)) {
      openCellAt(cellPosition);
      changeGameStatusToLose();
      return;
    }

    openSurroundedCells(cellPosition);
    checkIfGameIsOver();
  }

  // 판별 //
  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
      || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public boolean isInProgress() {
    return gameStatus == GameStatus.IN_PROGRESS;
  }

  public boolean isLoseStatus() {
    return gameStatus == GameStatus.LOSE;
  }

  public boolean isWinStatus() {
    return gameStatus == GameStatus.WIN;
  }

  // 조회 //
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

  private void initializeGameStatus() {
    gameStatus = GameStatus.IN_PROGRESS;
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

  private void openSurroundedCells(CellPosition cellPosition) {
    if (isOpenedCell(cellPosition)) {
      return;
    }
    if (isLandMineCellAt(cellPosition)) {
      return;
    }

    openCellAt(cellPosition);

    if (doesCellHaveLandMineCount(cellPosition)) {
      return;
    }

    List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition);
    surroundedPositions.forEach(this::openSurroundedCells);
  }

  private void openCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  private boolean isLandMineCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isLandMine();
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  private boolean isAllCellChecked() {
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
  }

  private void checkIfGameIsOver() {
    if (isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

  private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.hasLandMineCount();
  }

  private void changeGameStatusToWin() {
    gameStatus = GameStatus.WIN;
  }

  private void changeGameStatusToLose() {
    gameStatus = GameStatus.LOSE;
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }
}
