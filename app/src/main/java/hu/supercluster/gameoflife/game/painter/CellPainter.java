package hu.supercluster.gameoflife.game.painter;

import android.graphics.Paint;

public interface CellPainter {
    Paint getPaint(int state);
}
