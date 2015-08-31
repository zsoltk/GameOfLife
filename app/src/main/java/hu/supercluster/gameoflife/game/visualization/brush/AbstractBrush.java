package hu.supercluster.gameoflife.game.visualization.brush;

import android.graphics.Point;

abstract public class AbstractBrush implements Brush {
    private Paintable paintable;
    private Point startingPoint;
    private int state;

    @Override
    public final void paint(Paintable paintable, Point relativeTo) {
        setPaintable(paintable);
        setStartingPoint(relativeTo);
        doPaint();
    }

    protected final void setPaintable(Paintable paintable) {
        this.paintable = paintable;
    }

    protected final void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    protected final void paintWith(int state) {
        this.state = state;
    }

    protected final void paint(int relX, int relY) {
        paint(relX, relY, state);
    }

    protected final void paint(int relX, int relY, int state) {
        paintable.paint(startingPoint.x + relX, startingPoint.y + relY, state);
    }

    protected abstract void doPaint();
}
