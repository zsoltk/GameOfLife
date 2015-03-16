package hu.supercluster.gameoflife.specs;

import android.test.suitebuilder.annotation.LargeTest;

import hu.supercluster.gameoflife.app.activity.main.MainActivity_;
import hu.supercluster.gameoflife.test.support.EspressoSpec;
import hu.supercluster.gameoflife.test.support.pages.StartPage;

@LargeTest
public class MainActivitySpec extends EspressoSpec<MainActivity_> {

    StartPage startPage = new StartPage();

    public MainActivitySpec() {
        super(MainActivity_.class);
    }

    public void testShouldShowText() {
        startPage.checkTextViewHasText("Hello Espresso!");
    }
}
