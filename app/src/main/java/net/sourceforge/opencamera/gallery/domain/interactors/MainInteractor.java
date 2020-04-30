package net.sourceforge.opencamera.gallery.domain.interactors;

import net.sourceforge.opencamera.gallery.domain.interfaces.interactors.MainInteractorInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainInteractor {
    private static MainInteractor instance;
    private ArrayList<MainInteractorInterface.MediaListener> listeners;

    public MainInteractor() {
        listeners = new ArrayList<MainInteractorInterface.MediaListener>();
    }

    public static MainInteractor getInstance() {
        if (instance == null)
            instance = new MainInteractor();
        return instance;
    }

    public void setEditMode(Boolean bool) {
        for (MainInteractorInterface.MediaListener listener : listeners)
            listener.setEditMode(bool);
    }

    public void deleteSelectedItems() {
        for (MainInteractorInterface.MediaListener listener : listeners)
            listener.deleteSelectedItems();
    }

    public void shareSelectedItems() {
        for (MainInteractorInterface.MediaListener listener : listeners)
            listener.shareSelectedItems();
    }

    public void addListener(MainInteractorInterface.MediaListener medialistener) {
        if (listeners.contains(medialistener))
            return;
        listeners.add(medialistener);
    }

    public void removeListener(MainInteractorInterface.MediaListener medialistener) {
        listeners.remove(medialistener);
    }
}
