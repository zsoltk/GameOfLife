package hu.supercluster.gameoflife.app.activity.main;

import android.app.Activity;
import android.widget.LinearLayout;

import hu.supercluster.gameoflife.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends Activity {
    @Bean
    MainPresenter presenter;

    @ViewById(R.id.activity_main)
    LinearLayout layout;

    @AfterViews
    void afterViews() {
        presenter.createGame();
    }
}
