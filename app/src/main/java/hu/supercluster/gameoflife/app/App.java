package hu.supercluster.gameoflife.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import hu.supercluster.gameoflife.BuildConfig;
import tslamic.github.io.adn.DeviceNames;

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
        Crashlytics.setString("Device model", DeviceNames.getCurrentDeviceName("Unknown"));
    }
}
