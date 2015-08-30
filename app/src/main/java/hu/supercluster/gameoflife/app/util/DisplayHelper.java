package hu.supercluster.gameoflife.app.util;

import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

@EBean
public class DisplayHelper {
    private Display display;

    @SystemService
    WindowManager windowManager;

    @AfterInject
    protected void init() {
        display = windowManager.getDefaultDisplay();
    }

    public Point getDisplaySize() {
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public int getScreenOrientation() {
        return display.getRotation();
    }
}
