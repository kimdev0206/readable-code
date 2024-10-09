package cleancode.minesweeper.tobe.minesweeper.level;

public class Beginner implements Level {

  @Override
  public int getRowSize() {
    return 8;
  }

  @Override
  public int getColSize() {
    return 10;
  }

  @Override
  public int getLandMineCount() {
    return 10;
  }

}
