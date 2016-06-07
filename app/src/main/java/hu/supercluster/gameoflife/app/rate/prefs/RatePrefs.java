package hu.supercluster.gameoflife.app.rate.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface RatePrefs {
    @DefaultInt(0)
    int totalStartupCount();

    @DefaultInt(0)
    int currentStartupCount();

    @DefaultBoolean(true)
    boolean shouldAskAgain();
}
