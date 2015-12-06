package hu.supercluster.gameoflife.test.support;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/** Enable shadows for our own application classes. */
public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }
}
