package ch.supsi.imageeditor.frontend.observer;

import java.util.ArrayList;
import java.util.List;

public class HelpSubject {

    protected final List<ObserverHelp> observerHelps = new ArrayList<>();

    public void registerObserver(ObserverHelp observerHelp) {
        observerHelps.add(observerHelp);
    }

    public void notifyObservers(String title, String howToUse, String closeButtonText) {
        for (ObserverHelp observer : observerHelps) {
            observer.update(title, howToUse, closeButtonText);
        }
    }

}
