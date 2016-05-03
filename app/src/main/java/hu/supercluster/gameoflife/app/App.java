package hu.supercluster.gameoflife.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import hu.supercluster.paperwork.Paperwork;
import io.fabric.sdk.android.Fabric;
import tslamic.github.io.adn.DeviceNames;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();
    }

    private void initCrashlytics() {
        Fabric.with(this, new Crashlytics());
        Paperwork paperwork = new Paperwork(this);

        String gitInfo = String.format(Locale.US, "%s (%s)", paperwork.get("gitInfo"), paperwork.get("gitBranch"));
        Crashlytics.setString("Git", gitInfo);
        Crashlytics.setString("SHA", paperwork.get("gitSha"));
        Crashlytics.setString("Build time", paperwork.get("buildTime"));
        Crashlytics.setString("Device model", DeviceNames.getCurrentDeviceName("Unknown"));
    }
}
