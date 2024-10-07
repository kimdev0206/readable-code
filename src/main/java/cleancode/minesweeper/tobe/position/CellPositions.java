package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

  private final List<CellPosition> positionList;

  private CellPositions(List<CellPosition> positionList) {
    this.positionList = positionList;
  }

  public static CellPositions of(List<CellPosition> positionList) {
    return new CellPositions(positionList);
  }

  public static CellPositions from(Cell[][] board) {
    List<CellPosition> positionList = new ArrayList<>();

    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        CellPosition cellPosition = CellPosition.of(row, col);
        positionList.add(cellPosition);
      }
    }

    return of(positionList);
  }

  public List<CellPosition> extractRandomPositions(int count) {
    List<CellPosition> newPositionList = new ArrayList<>(positionList);
    Collections.shuffle(newPositionList);

    return newPositionList.subList(0, count);
  }

  public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
    List<CellPosition> newPositionList = new ArrayList<>(positionList);
    CellPositions positionsToSubtract = CellPositions.of(positionListToSubtract);

    return newPositionList.stream()
      .filter(positionsToSubtract::doesNotContain)
      .toList();
  }

  private boolean doesNotContain(CellPosition position) {
    return !positionList.contains(position);
  }

  public List<CellPosition> getPositionList() {
    return new ArrayList<>(positionList);
  }
}
