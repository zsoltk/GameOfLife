package hu.supercluster.gameoflife.game.manager;

import android.content.Context;

import hu.supercluster.gameoflife.game.view.AutomatonView;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomatonFactory;

public class GameManager {
    public static void create(Context context, AutomatonView automatonView, CellularAutomatonFactory factory, GameParams params) {
        initView(
                automatonView,
                createAutomaton(factory, params),
                params
        );
    }

    private static void initView(AutomatonView automatonView, CellularAutomaton automaton, GameParams params) {
        automatonView.init(
                automaton,
                params
        );
    }

    private static CellularAutomaton createAutomaton(CellularAutomatonFactory factory, GameParams params) {
        CellularAutomaton<?> automaton = factory.create(
                params.getGridSizeX(),
                params.getGridSizeY()
        );

        automaton.randomFill(params.getFill());

        return automaton;
    }
}
