package hu.supercluster.gameoflife.game.manager;

import hu.supercluster.gameoflife.game.view.AutomatonView;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomatonFactory;

public class GameManager {
    private static CellularAutomaton automaton;
    private final AutomatonView automatonView;
    private final GameParams params;

    public GameManager(AutomatonView automatonView, CellularAutomatonFactory factory, GameParams params) {
        this.automatonView = automatonView;
        this.params = params;

        automaton = createAutomaton(factory, params);
    }

    public void createGame() {
        initView(
                automatonView,
                automaton,
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

    public static CellularAutomaton getAutomaton() {
        return automaton;
    }
}
