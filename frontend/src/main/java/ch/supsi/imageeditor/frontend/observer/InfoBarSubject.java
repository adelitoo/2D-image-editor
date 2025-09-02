package ch.supsi.imageeditor.frontend.observer;

import java.util.ArrayList;
import java.util.List;

public class InfoBarSubject {
    protected final List<ObserverInfoBar> observerInfoBars = new ArrayList<>();

    public void registerObserver(ObserverInfoBar observerInfoBar) {
        observerInfoBars.add(observerInfoBar);
    }
    
    public void notifyObservers(String message) {
        for (ObserverInfoBar observerInfoBar : observerInfoBars) {
            observerInfoBar.update(message);
        }
    }
}
