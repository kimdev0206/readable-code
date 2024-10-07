package cleancode.minesweeper.tobe.cell;

public interface Cell {

  CellSnapshot getSnapshot();

  boolean isLandMine();

  boolean hasLandMineCount();

  void flag();

  void open();

  boolean isChecked();

  boolean isOpened();

}
