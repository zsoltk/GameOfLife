package hu.supercluster.gameoflife.component.tests;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.activity.main.MainActivity_;
import hu.supercluster.gameoflife.component.tests.pages.StartPage;
import hu.supercluster.gameoflife.test.support.ComponentTestSpecification;

import org.junit.Before;
import org.junit.Test;

public class MainActivitySpec extends ComponentTestSpecification<MainActivity_> {

    StartPage startPage = new StartPage(this);

    public MainActivitySpec() {
        super(MainActivity_.class);
    }

    @Before
    public void setUp() {
        startActivity();
    }

    @Test
    public void testShouldUseCorrectLayout() throws Exception {
        startPage.checkLayoutIs(R.id.activity_main);
    }

    @Test
    public void testShouldShowText() throws Exception {
        startPage.checkTextViewHasText("Hello Espresso!");
    }

}
