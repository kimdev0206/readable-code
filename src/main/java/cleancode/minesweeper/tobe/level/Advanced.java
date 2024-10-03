package cleancode.minesweeper.tobe.level;

public class Advanced implements Level {

  @Override
  public int getRowSize() {
    return 20;
  }

  @Override
  public int getColSize() {
    return 24;
  }

  @Override
  public int getLandMineCount() {
    return 99;
  }

}
