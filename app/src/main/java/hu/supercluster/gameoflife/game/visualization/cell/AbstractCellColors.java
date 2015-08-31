package hu.supercluster.gameoflife.game.visualization.cell;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

public class AbstractCellColors implements CellColors {
    protected final Map<Integer, Paint> paintMap;

    public AbstractCellColors() {
        paintMap = new HashMap<>(2);
    }

    protected Paint createPaint(String color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));

        return paint;
    }

    @Override
    public Paint getPaint(int state) {
        return paintMap.get(state);
    }
}
