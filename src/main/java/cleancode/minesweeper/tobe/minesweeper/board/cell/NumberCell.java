package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class NumberCell implements Cell {

  private final CellState state = CellState.initialize();
  private final int nearbyLandMineCount;

  public NumberCell(int nearbyLandMineCount) {
    this.nearbyLandMineCount = nearbyLandMineCount;
  }

  @Override
  public CellSnapshot getSnapshot() {
    if (state.isOpened()) {
      return CellSnapshot.ofNumber(nearbyLandMineCount);
    }

    if (state.isFlagged()) {
      return CellSnapshot.ofFlag();
    }

    return CellSnapshot.ofUnchecked();
  }

  @Override
  public boolean isLandMine() {
    return false;
  }

  @Override
  public boolean hasLandMineCount() {
    return true;
  }

  @Override
  public void flag() {
    state.flag();
  }

  @Override
  public void open() {
    state.open();
  }

  @Override
  public boolean isChecked() {
    return state.isOpened();
  }

  @Override
  public boolean isOpened() {
    return state.isOpened();
  }

}
