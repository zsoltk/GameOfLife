package hu.supercluster.gameoflife.app.activity.main;

import android.graphics.Point;
import android.view.Display;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.game.event.Pause;
import hu.supercluster.gameoflife.game.event.Resume;
import hu.supercluster.gameoflife.game.view.ChangeRulesDialogFragment;
import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cellularautomaton.GameOfLifeFactory;
import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.manager.GameManager;
import hu.supercluster.gameoflife.game.manager.GameParams;
import hu.supercluster.gameoflife.game.manager.GameParamsBuilder;
import hu.supercluster.gameoflife.game.event.Reset;
import hu.supercluster.gameoflife.game.event.Restart;
import hu.supercluster.gameoflife.game.painter.SimpleCellPainter;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;
import hu.supercluster.gameoflife.util.EventBus;

import static android.view.View.*;

@EBean
public class MainPresenter {
    private GameManager gameManager;

    @RootContext
    MainActivity activity;

    @AfterInject
    void registerOnEventBus() {
        EventBus.getInstance().register(this);
    }

    protected void createGame() {
        GameParams gameParams = GameParamsBuilder.create()
                .setDisplaySize(getDisplaySize())
                .setCellSizeInPixels(6)
                .setFill(new Fill(0.10f, Cell.STATE_ALIVE))
                .setCellPainter(new SimpleCellPainter())
                .setFps(15)
                .build();

        gameManager = new GameManager(
                activity.automatonView,
                new GameOfLifeFactory(),
                gameParams
        );

        gameManager.createGame();
    }

    private Point getDisplaySize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    void onPause() {
        EventBus.getInstance().post(new Pause());
        setUiPausedState(true);
    }

    void onResume() {
        EventBus.getInstance().post(new Resume());
        setUiPausedState(false);
    }

    private void setUiPausedState(boolean paused) {
        activity.resume.setVisibility(paused ? VISIBLE : GONE);
        activity.pause.setVisibility(paused ? GONE : VISIBLE);
        activity.paused = paused;
    }

    void onResetGame() {
        EventBus.getInstance().post(new Reset());
    }

    void onRestartGame() {
        EventBus.getInstance().post(new Restart());
    }

    void onChangeRules() {
        ChangeRulesDialogFragment dialogFragment = new ChangeRulesDialogFragment();
        dialogFragment.setRule((NeighborCountBasedRule) gameManager.getAutomaton().getRule());
        dialogFragment.show(activity.getFragmentManager(), "rules");
    }

    @Subscribe
    public void onRulesChanged(NeighborCountBasedRule rule) {
        gameManager.getAutomaton().setRule(rule);
    }
}
