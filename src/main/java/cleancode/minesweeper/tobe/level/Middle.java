package cleancode.minesweeper.tobe.level;

public class Middle implements Level {

  @Override
  public int getRowSize() {
    return 14;
  }

  @Override
  public int getColSize() {
    return 18;
  }

  @Override
  public int getLandMineCount() {
    return 40;
  }

}
