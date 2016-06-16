package hu.supercluster.gameoflife.app.activity.main;

import android.graphics.Point;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.app.rate.event.RatingDialogLaterEvent;
import hu.supercluster.gameoflife.app.rate.event.RatingDialogNoEvent;
import hu.supercluster.gameoflife.app.rate.event.RatingDialogYesEvent;
import hu.supercluster.gameoflife.app.rate.event.SuggestDialogNoEvent;
import hu.supercluster.gameoflife.app.rate.event.SuggestDialogYesEvent;
import hu.supercluster.gameoflife.app.rate.util.RatingHelper;
import hu.supercluster.gameoflife.app.util.DisplayHelper;
import hu.supercluster.gameoflife.app.view.ChangeRulesDialogFragment;
import hu.supercluster.gameoflife.app.view.ChangeRulesDialogFragment_;
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
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;
import hu.supercluster.gameoflife.game.visualization.cell.SimpleCellColors;
import hu.supercluster.gameoflife.util.EventBus;
import hugo.weaving.DebugLog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EBean
public class MainPresenter {
    GameManager gameManager;

    @RootContext
    MainActivity activity;

    @Bean
    DisplayHelper displayHelper;

    @Bean
    RatingHelper ratingHelper;

    void onActivityCreate() {
        if (!orientationChanged()) {
            ratingHelper.increaseStartupCount();
        }

        saveOrientation();
    }

    private boolean orientationChanged() {
        return displayHelper.getScreenOrientation() != activity.lastOrientation;
    }

    private void saveOrientation() {
        activity.lastOrientation = displayHelper.getScreenOrientation();
    }

    void onActivityResume() {
        EventBus.getInstance().register(this);
        ratingHelper.displaySuggestDialogIfNeeded();
    }

    void onActivityPause() {
        EventBus.getInstance().unregister(this);
    }

    public Bundle saveGameState() {
        return gameManager.saveGameState();
    }

    void startGame() {
        gameManager = new GameManager(
                activity.gameState,
                activity.automatonView,
                new GameOfLifeFactory(),
                getGameParams()
        );

        gameManager.createGame();

        if (activity.paused) {
            onPause();
        }
    }

    private GameParams getGameParams() {
        Point displaySize = displayHelper.getDisplaySize();
        int cellSize = getOptimalCellSize(displaySize, displayHelper.isLandscape());

        return GameParamsBuilder.create()
                .setScreenOrientation(displayHelper.getScreenOrientation())
                .setDisplaySize(displaySize)
                .setCellSizeInPixels(cellSize)
                .setFill(new Fill(0.10f, Cell.STATE_ALIVE))
                .setCellColors(new SimpleCellColors())
                .setFps(15)
                .startPaused(activity.paused)
                .build()
        ;
    }

    private int getOptimalCellSize(Point displaySize, boolean isLandscape) {
        int cellSize = 1;
        int limitX = 160;
        int limitY = 200;

        if (isLandscape) {
            int temp = limitX;
            limitX = limitY;
            limitY = temp;
        }

        while (displaySize.x / cellSize > limitX || displaySize.y / cellSize > limitY) {
            cellSize++;
        }

        return cellSize;
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
        ChangeRulesDialogFragment dialogFragment = ChangeRulesDialogFragment_.builder().build();
        dialogFragment.setRule((NeighborCountBasedRule) gameManager.getAutomaton().getRule());
        dialogFragment.show(activity.getFragmentManager(), "rules");
    }

    @DebugLog
    @Subscribe
    public void onRulesChanged(NeighborCountBasedRule rule) {
        gameManager.getAutomaton().setRule(rule);
    }

    @DebugLog
    @Subscribe
    public void onEvent(SuggestDialogYesEvent event) {
        ratingHelper.displayRatingDialog();
    }

    @DebugLog
    @Subscribe
    public void onEvent(SuggestDialogNoEvent event) {
        ratingHelper.onDontAskAgain();
    }

    @DebugLog
    @Subscribe
    public void onEvent(RatingDialogYesEvent event) {
        ratingHelper.onRate();
    }

    @DebugLog
    @Subscribe
    public void onEvent(RatingDialogLaterEvent event) {
        ratingHelper.onRateLater();
    }

    @DebugLog
    @Subscribe
    public void onEvent(RatingDialogNoEvent event) {
        ratingHelper.onDontAskAgain();
    }
}
