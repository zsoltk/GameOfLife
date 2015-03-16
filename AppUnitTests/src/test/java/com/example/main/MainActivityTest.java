package com.example.main;

import android.widget.Button;
import com.example.R;
import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MainActivityTest extends UnitTestSpecification {

    @Mock
    MainPresenter presenter;

    MainActivity_ view = Robolectric.buildActivity(MainActivity_.class).create().get();

    private Button testButton;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view.presenter = presenter;
        testButton = (Button) view.findViewById(R.id.testButton);
    }

    @Test
    public void testDummy() {
        testButton.performClick();
        verify(presenter).alert();
    }
}
