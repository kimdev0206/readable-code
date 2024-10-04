package cleancode.minesweeper.tobe.cell;

public interface Cell {

  String FLAG_SIGN = "⚑";
  String UNCHECKED_SIGN = "□";

  String getSign();

  boolean isLandMine();

  boolean hasLandMineCount();

  void flag();

  void open();

  boolean isChecked();

  boolean isOpened();

}
