package hu.supercluster.gameoflife.util;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class EventBus extends Bus {
    private static EventBus instance;

    private EventBus() {
        super(ThreadEnforcer.ANY);
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }

        return instance;
    }

    @Override
    public void unregister(Object object) {
        try {
            super.unregister(object);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
