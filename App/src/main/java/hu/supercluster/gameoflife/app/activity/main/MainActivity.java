package hu.supercluster.gameoflife.app.activity.main;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import hu.supercluster.gameoflife.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Bean
    MainPresenter presenter;

    @Click
    void testButton() {
        presenter.alert();
    }
}
