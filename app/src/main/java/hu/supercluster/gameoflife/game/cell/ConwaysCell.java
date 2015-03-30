package hu.supercluster.gameoflife.game.cell;

public class ConwaysCell implements Cell {
    int state;
    int neighborCount;

    public ConwaysCell(int state) {
        setState(state);
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean isAlive() {
        return !isDead();
    }

    @Override
    public boolean isDead() {
        return state == STATE_DEAD;
    }

    public void increaseNeighborCount() {
        neighborCount++;
    }

    public void decreaseNeighborCount() {
        neighborCount--;
    }

    public int getNeighborCount() {
        return neighborCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConwaysCell that = (ConwaysCell) o;

        if (neighborCount != that.neighborCount) return false;
        if (state != that.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state;
        result = 31 * result + neighborCount;

        return result;
    }
}
