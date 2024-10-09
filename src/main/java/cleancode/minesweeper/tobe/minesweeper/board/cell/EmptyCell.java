package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class EmptyCell implements Cell {

  private final CellState state = CellState.initialize();

  @Override
  public CellSnapshot getSnapshot() {
    if (state.isOpened()) {
      return CellSnapshot.ofEmpty();
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
    return false;
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
