package cleancode.minesweeper.tobe.cell;

public class State {

  private boolean isFlagged;
  private boolean isOpened;

  public State(boolean isFlagged, boolean isOpened) {
    this.isFlagged = isFlagged;
    this.isOpened = isOpened;
  }

  public static State initialize() {
    return new State(false, false);
  }

  public void flag() {
    isFlagged = true;
  }

  public void open() {
    isOpened = true;
  }

  public boolean isChecked() {
    return isFlagged || isOpened;
  }

  public boolean isOpened() {
    return isOpened;
  }

  public boolean isFlagged() {
    return isFlagged;
  }
}
