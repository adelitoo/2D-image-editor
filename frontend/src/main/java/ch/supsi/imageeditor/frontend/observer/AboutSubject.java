package ch.supsi.imageeditor.frontend.observer;

import java.util.ArrayList;
import java.util.List;

public class AboutSubject {
    protected final List<ObserverAbout> observerAbouts = new ArrayList<>();

    public void registerObserver(ObserverAbout observerAbout) {
        observerAbouts.add(observerAbout);
    }

    public void notifyObservers(String name, String developer, String version, String date) {
        for (ObserverAbout observerAbout : observerAbouts) {
            observerAbout.update(name, developer, version, date);
        }
    }
}
