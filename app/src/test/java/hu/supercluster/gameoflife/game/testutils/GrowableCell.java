package hu.supercluster.gameoflife.game.testutils;

import java.util.concurrent.atomic.AtomicLong;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.Overseer;

public class GrowableCell implements Cell {
    static final AtomicLong NEXT_ID = new AtomicLong(0);
    final long id = NEXT_ID.getAndIncrement();

    final int x;
    final int y;
    int state;
    Overseer overseer;

    public GrowableCell(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    @Override
    public void setOverseer(Overseer overseer) {
        this.overseer = overseer;
    }

    @Override
    public void reset(int state) {
        this.state = state;
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
        this.state = newState;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrowableCell that = (GrowableCell) o;

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
        return result;
    }

    @Override
    public String toString() {
        return "GrowableCell{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", state=" + state +
                '}';
    }
}
