package hu.supercluster.gameoflife.test.support;

import android.content.Context;

import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

import java.io.File;

public abstract class DatabaseSpecification extends RobolectricTest {

    public Context context;

    @Before
    public void initContext() {
        context = RuntimeEnvironment.application;
        resetDatabase();
    }

    public void resetDatabase() {
        File dbFile = context.getDatabasePath(null);
        if (dbFile.exists()) {
            context.deleteDatabase(dbFile.getAbsolutePath());
        }
    }
}
