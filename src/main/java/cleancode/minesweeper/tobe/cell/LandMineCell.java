package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {

  private final State state = State.initialize();

  @Override
  public CellSnapshot getSnapshot() {
    if (state.isOpened()) {
      return CellSnapshot.ofLandMine();
    }

    if (state.isFlagged()) {
      return CellSnapshot.ofFlag();
    }

    return CellSnapshot.ofUnchecked();
  }

  @Override
  public boolean isLandMine() {
    return true;
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
    return state.isChecked();
  }

  @Override
  public boolean isOpened() {
    return state.isOpened();
  }

}
