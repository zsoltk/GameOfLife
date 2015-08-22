package hu.supercluster.gameoflife.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();
    }

    private void initCrashlytics() {
        Crashlytics.start(this);
    }
}
