package hu.supercluster.gameoflife.game.creator;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import hu.supercluster.gameoflife.app.activity.automaton.AutomatonView;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomatonFactory;

public class GameCreator {
    public static void create(Context context, LinearLayout layout, CellularAutomatonFactory factory, GameParams params) {
        CellularAutomaton automaton = createAutomaton(factory, params);
        SurfaceView surfaceView = createAutomatonView(context, automaton, params);
        layout.addView(surfaceView, 0);
    }

    private static CellularAutomaton createAutomaton(CellularAutomatonFactory factory, GameParams params) {
        CellularAutomaton<?> automaton = factory.create(
                params.getGridSizeX(),
                params.getGridSizeY()
        );

        automaton.randomFill(params.getFill());

        return automaton;
    }

    private static SurfaceView createAutomatonView(Context context, CellularAutomaton automaton, GameParams params) {
        return new AutomatonView(
                context,
                automaton,
                params.getCellSizeInPixels(),
                params.getFps()
        );
    }
}
