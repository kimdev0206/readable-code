package cleancode.minesweeper.tobe.cell;

public class EmptyCell implements Cell {

  private static final String EMPTY_SIGN = "■";

  private final State state = State.initialize();

  @Override
  public String getSign() {
    if (state.isOpened()) {
      return EMPTY_SIGN;
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
