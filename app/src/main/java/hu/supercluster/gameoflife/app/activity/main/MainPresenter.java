package hu.supercluster.gameoflife.app.activity.main;

import android.os.Bundle;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.app.util.DisplayHelper;
import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.cellularautomaton.GameOfLifeFactory;
import hu.supercluster.gameoflife.game.event.Pause;
import hu.supercluster.gameoflife.game.event.Reset;
import hu.supercluster.gameoflife.game.event.Restart;
import hu.supercluster.gameoflife.game.event.Resume;
import hu.supercluster.gameoflife.game.manager.GameManager;
import hu.supercluster.gameoflife.game.manager.GameParams;
import hu.supercluster.gameoflife.game.manager.GameParamsBuilder;
import hu.supercluster.gameoflife.game.visualization.cell.SimpleCellColors;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;
import hu.supercluster.gameoflife.app.view.ChangeRulesDialogFragment;
import hu.supercluster.gameoflife.util.EventBus;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EBean
public class MainPresenter {
    GameManager gameManager;

    @RootContext
    MainActivity activity;

    @Bean
    DisplayHelper displayHelper;

    void onActivityResume() {
        EventBus.getInstance().register(this);
    }

    void onActivityPause() {
        EventBus.getInstance().unregister(this);
    }

    public Bundle saveGameState() {
        return gameManager.saveGameState();
    }

    void startGame() {
        GameParams gameParams = GameParamsBuilder.create()
                .setScreenOrientation(displayHelper.getScreenOrientation())
                .setDisplaySize(displayHelper.getDisplaySize())
                .setCellSizeInPixels(6)
                .setFill(new Fill(0.10f, Cell.STATE_ALIVE))
                .setCellColors(new SimpleCellColors())
                .setFps(15)
                .startPaused(activity.paused)
                .build()
        ;

        gameManager = new GameManager(
                activity.gameState,
                activity.automatonView,
                new GameOfLifeFactory(),
                gameParams
        );

        gameManager.createGame();

        if (activity.paused) {
            onPause();
        }
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
        onPause();
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
