package hu.supercluster.gameoflife.component.tests.pages;

import android.widget.TextView;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.test.support.ComponentTestSpecification;
import hu.supercluster.gameoflife.test.support.assertions.AndroidShadowAssertions;

import static org.fest.assertions.api.ANDROID.assertThat;

public class StartPage {

    private ComponentTestSpecification componentTestSpecification;

    public StartPage(ComponentTestSpecification componentTestSpecification) {
        this.componentTestSpecification = componentTestSpecification;
    }

    public void checkTextViewHasText(String expected) {
//        TextView textView = (TextView) componentTestSpecification.activity.findViewById(R.id.text);
//        assertThat(textView).containsText(expected);
    }

    public void checkLayoutIs(int expected) {
//        AndroidShadowAssertions.assertThat(componentTestSpecification.activityShadow).hasContentView(expected);
    }
}
