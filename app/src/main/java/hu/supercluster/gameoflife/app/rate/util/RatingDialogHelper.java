package hu.supercluster.gameoflife.app.rate.util;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.app.rate.dialog.RatingDialog_;
import hu.supercluster.gameoflife.app.rate.dialog.SuggestDialog_;

@EBean
public class RatingDialogHelper {
    private static final String TAG_SUGGEST = "suggest";
    private static final String TAG_RATING = "rating";

    @RootContext
    Activity activity;

    public void displaySuggestDialog() {
        show(SuggestDialog_.builder().build(), TAG_SUGGEST);
    }

    public void displayRatingDialog() {
        show(RatingDialog_.builder().build(), TAG_RATING);
    }

    private void show(DialogFragment dialogFragment, String tag) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);

        if (fragmentByTag == null) {
            dialogFragment.show(fragmentManager, tag);
        }
    }
}
