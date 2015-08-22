package hu.supercluster.gameoflife.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import hu.supercluster.gameoflife.BuildConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();
    }

    private void initCrashlytics() {
        Crashlytics.start(this);
        Crashlytics.setString("Build time", BuildConfig.BUILD_TIME);
        Crashlytics.setString("Git SHA", BuildConfig.GIT_SHA);
    }
}
