package cleancode.minesweeper.tobe.position;

import java.util.Objects;

public class CellPosition {

  private final int rowIndex;
  private final int colIndex;

  private CellPosition(int rowIndex, int colIndex) {
    if (rowIndex < 0 || colIndex < 0) {
      throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
    }

    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  public static CellPosition of(int rowIndex, int colIndex) {
    return new CellPosition(rowIndex, colIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CellPosition cellPosition = (CellPosition) o;
    return rowIndex == cellPosition.rowIndex && colIndex == cellPosition.colIndex;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowIndex, colIndex);
  }

  public boolean isRowIndexMoreThanOrEqual(int rowIndex) {
    return this.rowIndex >= rowIndex;
  }

  public boolean isColIndexMoreThanOrEqual(int colIndex) {
    return this.colIndex >= colIndex;
  }

  public boolean isRowIndexLessThan(int rowIndex) {
    return this.rowIndex < rowIndex;
  }

  public boolean isColIndexLessThan(int colIndex) {
    return this.colIndex < colIndex;
  }

  public int getRowIndex() {
    return rowIndex;
  }

  public int getColIndex() {
    return colIndex;
  }

  public CellPosition calculatePositionBy(RelativePosition relativePosition) {
    if (canCalculatePositionBy(relativePosition)) {
      return CellPosition.of(
        rowIndex + relativePosition.getDeltaRow(),
        colIndex + relativePosition.getDeltaCol()
      );
    }

    throw new IllegalArgumentException("움직일 수 있는 좌표가 아닙니다.");
  }

  public boolean canCalculatePositionBy(RelativePosition relativePosition) {
    return rowIndex + relativePosition.getDeltaRow() >= 0
      && colIndex + relativePosition.getDeltaCol() >= 0;
  }
}
