package hu.supercluster.gameoflife.game.cell;

public interface CellStateChangeCallback {
    void onCellStateChanged(CellStateChange cellStateChange);
}
