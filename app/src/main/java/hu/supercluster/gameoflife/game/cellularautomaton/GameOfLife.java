package hu.supercluster.gameoflife.game.cellularautomaton;

import android.os.Parcel;

import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.cell.SimpleCellFactory;
import hu.supercluster.gameoflife.game.rule.ConwaysRule;
import hu.supercluster.gameoflife.game.rule.Rule;

public class GameOfLife extends AbstractCellularAutomaton<SimpleCell> {
    public GameOfLife(int gridSizeX, int gridSizeY) {
        super(gridSizeX, gridSizeY);
    }

    public GameOfLife(Parcel source) {
        super(source);
    }

    @Override
    protected CellFactory<SimpleCell> getFactory() {
        return new SimpleCellFactory();
    }

    @Override
    public Rule<SimpleCell> createRule() {
        return new ConwaysRule();
    }

    public static final Creator<GameOfLife> CREATOR = new Creator<GameOfLife>() {
        public GameOfLife createFromParcel(Parcel source) {
            return new GameOfLife(source);
        }

        public GameOfLife[] newArray(int size) {
            return new GameOfLife[size];
        }
    };
}
