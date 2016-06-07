package hu.supercluster.gameoflife.app.rate.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import hu.supercluster.gameoflife.app.rate.prefs.RatePrefs_;
import hugo.weaving.DebugLog;

@EBean
public class RatingHelper {
    private static final int THRESHOLD = 3;

    @RootContext
    Activity activity;

    @Pref
    RatePrefs_ ratePrefs;

    @Bean
    RatingDialogHelper ratingDialogHelper;

    public void onStartup() {
        increaseStartupCount();
    }

    @DebugLog
    private void increaseStartupCount() {
        ratePrefs.totalStartupCount().put(ratePrefs.totalStartupCount().get() + 1);
        ratePrefs.currentStartupCount().put(ratePrefs.currentStartupCount().get() + 1);
    }

    public void displaySuggestDialogIfNeeded() {
        boolean shouldAsk = ratePrefs.shouldAskAgain().get();
        int totalStartupCount = ratePrefs.totalStartupCount().get();
        int currentStartupCount = ratePrefs.currentStartupCount().get();

        if (shouldAsk && currentStartupCount >= THRESHOLD) {
            if (totalStartupCount > currentStartupCount) {
                ratingDialogHelper.displayRatingDialog();
            } else {
                ratingDialogHelper.displaySuggestDialog();
            }
        }
    }

    public void displayRatingDialog() {
        ratingDialogHelper.displayRatingDialog();
    }

    public void onRate() {
        final String packageName = getPackageName();
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = getViewIntent(uri);

        try {
            activity.startActivity(intent);
            onRated();

        } catch (ActivityNotFoundException e) {
            uri = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
            onRated();
        }
    }

    @DebugLog
    private String getPackageName() {
        String packageName = activity.getPackageName();

        if (packageName.endsWith(".debug")) {
            packageName = packageName.replace(".debug", "");
        }

        return packageName;
    }

    @DebugLog
    private Intent getViewIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        setFlags(intent);

        return intent;
    }

    private void setFlags(Intent intent) {
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        );
    }

    @DebugLog
    public void onRated() {
        ratePrefs.shouldAskAgain().put(false);
    }

    @DebugLog
    public void onRateLater() {
        ratePrefs.shouldAskAgain().put(true);
        ratePrefs.currentStartupCount().put(0);
    }

    @DebugLog
    public void onDontAskAgain() {
        ratePrefs.shouldAskAgain().put(false);
    }
}
