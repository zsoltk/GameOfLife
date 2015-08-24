package hu.supercluster.gameoflife.game.cell;

import java.util.concurrent.atomic.AtomicLong;

import hu.supercluster.gameoflife.game.event.CellStateChange;
import hu.supercluster.gameoflife.util.EventBus;

public class SimpleCell implements Cell {
    static final AtomicLong NEXT_ID = new AtomicLong(0);
    final long id = NEXT_ID.getAndIncrement();
    final int x;
    final int y;
    int state;
    int neighborCount;

    public SimpleCell(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    @Override
    public void reset(int state) {
        this.state = state;
        neighborCount = 0;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int newState) {
        int oldState = this.state;
        this.state = newState;

        if (newState != oldState) {
            EventBus.getInstance().post(new CellStateChange(this));
        }
    }

    @Override
    public boolean isAlive() {
        return !isDead();
    }

    @Override
    public boolean isDead() {
        return state == STATE_DEAD;
    }

    @Override
    public void onNeighborStateChange(int newState) {
        if (newState == STATE_DEAD) {
            decreaseNeighborCount();

        } else {
            increaseNeighborCount();
        }
    }

    void increaseNeighborCount() {
        neighborCount++;
    }

    void decreaseNeighborCount() {
        neighborCount--;
    }

    public int getNeighborCount() {
        return neighborCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleCell that = (SimpleCell) o;

        if (neighborCount != that.neighborCount) return false;
        if (state != that.state) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + state;
        result = 31 * result + neighborCount;
        return result;
    }
}
