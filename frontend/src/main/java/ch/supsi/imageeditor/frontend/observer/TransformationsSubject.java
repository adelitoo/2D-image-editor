package ch.supsi.imageeditor.frontend.observer;

import java.util.ArrayList;
import java.util.List;

public class TransformationsSubject {
    protected final List<ObserverTransformations> observerTransformations = new ArrayList<>();

    public void registerObserver(ObserverTransformations observer) {
        observerTransformations.add(observer);
    }

    public void notifyObservers(List<String> selectedTransformations) {
        for (ObserverTransformations observer : observerTransformations) {
            observer.update(selectedTransformations);
        }
    }

}
