package hu.supercluster.gameoflife.game.grid;

public class EndlessGridHandler implements GridHandler {
    private final int x;
    private final int y;
    Grid currentGrid;

    public EndlessGridHandler(int x, int y) {
        this.x = x;
        this.y = y;
        currentGrid = createNew();
    }

    @Override
    public Grid getCurrent() {
        return new EndlessGrid(currentGrid);
    }

    @Override
    public Grid createNew() {
        return new EndlessGrid(x, y);
    }

    @Override
    public void setCurrent(Grid grid) {
        currentGrid = new EndlessGrid(grid);
    }
}
