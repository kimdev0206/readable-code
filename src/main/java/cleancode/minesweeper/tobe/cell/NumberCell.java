package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

  private final State state = State.initialize();
  private final int nearbyLandMineCount;

  public NumberCell(int nearbyLandMineCount) {
    this.nearbyLandMineCount = nearbyLandMineCount;
  }

  @Override
  public String getSign() {
    if (state.isOpened()) {
      return String.valueOf(nearbyLandMineCount);
    }

    if (state.isFlagged()) {
      return FLAG_SIGN;
    }

    return UNCHECKED_SIGN;
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
    return state.isChecked();
  }

  @Override
  public boolean isOpened() {
    return state.isOpened();
  }

}
