package hu.supercluster.gameoflife.game.cell;

import hu.supercluster.gameoflife.game.event.CellStateChange;

public interface Overseer {
    void onCellStateChanged(CellStateChange cellStateChange);
}
