package hu.supercluster.gameoflife.app.activity.main;

import android.app.Activity;
import android.widget.ImageButton;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.game.view.AutomatonView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends Activity {
    @InstanceState
    boolean paused;

    @Bean
    MainPresenter presenter;

    @ViewById
    AutomatonView automatonView;

    @ViewById
    ImageButton reset, restart, changeRules, pause, resume;

    @AfterViews
    void afterViews() {
        presenter.createGame();
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
