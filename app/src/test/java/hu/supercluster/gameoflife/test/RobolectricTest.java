package hu.supercluster.gameoflife.test;

import android.os.Build;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import hu.supercluster.gameoflife.BuildConfig;

@RunWith(RobolectricGradleTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.JELLY_BEAN,
        packageName = "hu.supercluster.gameoflife"
)
abstract public class RobolectricTest {

}