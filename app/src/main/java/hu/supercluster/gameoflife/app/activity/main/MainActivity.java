package hu.supercluster.gameoflife.app.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.game.view.AutomatonView;
import hugo.weaving.DebugLog;

@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends Activity {
    @InstanceState
    boolean paused;

    @InstanceState
    Bundle gameState;

    @Bean
    MainPresenter presenter;

    @ViewById
    AutomatonView automatonView;

    @ViewById
    ImageButton reset, restart, changeRules, pause, resume;


    @Override
    @DebugLog
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        gameState = presenter.saveGameState();
    }

    @AfterViews
    void afterViews() {
        presenter.startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onActivityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onActivityPause();
    }

    @Click
    void pause() {
        presenter.onPause();
    }

    @Click
    void resume() {
        presenter.onResume();
    }

    @Click
    void reset() {
        presenter.onResetGame();
    }

    @Click
    void restart() {
        presenter.onRestartGame();
    }

    @Click
    void changeRules() {
        presenter.onChangeRules();
    }
}
