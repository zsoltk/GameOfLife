package hu.supercluster.gameoflife.app.activity.main;

import android.content.Intent;

import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MainPresenterTest extends UnitTestSpecification {

    @Mock
    MainActivity view;

    @InjectMocks
    MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);

    @Test
    public void testDummy() {
        assertThat(true).isEqualTo(true);
    }
}
