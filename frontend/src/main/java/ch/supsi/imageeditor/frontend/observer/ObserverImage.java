package ch.supsi.imageeditor.frontend.observer;

import ch.supsi.imageeditor.frontend.model.dto.PixelView;

public interface ObserverImage {
    void update(PixelView[][] pixels);
}
