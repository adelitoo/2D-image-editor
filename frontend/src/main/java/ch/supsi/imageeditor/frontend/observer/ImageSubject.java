package ch.supsi.imageeditor.frontend.observer;

import ch.supsi.imageeditor.frontend.model.dto.PixelView;

import java.util.ArrayList;
import java.util.List;

public class ImageSubject {
    protected final List<ObserverImage> observerImages = new ArrayList<>();

    public void registerObserver(ObserverImage observerImage) {
        observerImages.add(observerImage);
    }

    public void notifyObservers(PixelView[][] pixels) {
        for (ObserverImage observerImage : observerImages) {
            observerImage.update(pixels);
        }
    }
}
