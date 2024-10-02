package cleancode.minesweeper.tobe;

public class Cell {

  private static final String FLAG_SIGN = "⚑";
  private static final String LAND_MINE_SIGN = "☼";
  private static final String CLOSED_CELL_SIGN = "□";
  private static final String OPENED_CELL_SIGN = "■";

  private final String sign;
  private int nearbyLandMineCount;
  private boolean isLandMine;

  private Cell(String sign, int nearbyLandMineCount, boolean isLandMine) {
    this.sign = sign;
    this.nearbyLandMineCount = nearbyLandMineCount;
    this.isLandMine = isLandMine;
  }

  public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine) {
    return new Cell(sign, nearbyLandMineCount, isLandMine);
  }

  public static Cell ofFlag() {
    return of(FLAG_SIGN);
  }

  public static Cell ofLandMine() {
    return of(LAND_MINE_SIGN);
  }

  public static Cell ofClosed() {
    return of(CLOSED_CELL_SIGN);
  }

  public static Cell ofOpened() {
    return of(OPENED_CELL_SIGN);
  }

  public static Cell ofNearbyLandMineCount(Integer i) {
    return of(String.valueOf(i));
  }

  public String getSign() {
    return this.sign;
  }

  public boolean isClosed() {
    return CLOSED_CELL_SIGN.equals(this.sign);
  }

  public boolean doesNotClosed() {
    return !isClosed();
  }
}
