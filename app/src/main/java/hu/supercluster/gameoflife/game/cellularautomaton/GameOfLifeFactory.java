package hu.supercluster.gameoflife.game.cellularautomaton;

public class GameOfLifeFactory implements CellularAutomatonFactory {
    @Override
    public CellularAutomaton<?> create(int gridSizeX, int gridSizeY) {
        return new GameOfLife(gridSizeX, gridSizeY);
    }
}
