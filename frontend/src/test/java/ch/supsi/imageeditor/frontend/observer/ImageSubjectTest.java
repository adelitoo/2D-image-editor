package ch.supsi.imageeditor.frontend.observer;

import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ImageSubjectTest {
    private ImageSubject imageSubject;

    @BeforeEach
    public void setup() {
        imageSubject = new ImageSubject();
    }

    @Test
    public void testRegisterObserver() {
        ObserverImage observerMock = Mockito.mock(ObserverImage.class);
        imageSubject.registerObserver(observerMock);
        Assertions.assertEquals(1, imageSubject.observerImages.size());
        Assertions.assertTrue(imageSubject.observerImages.contains(observerMock));
    }

    @Test
    public void testNotifyObserversSingleObserver() {
        ObserverImage observerMock = Mockito.mock(ObserverImage.class);
        imageSubject.registerObserver(observerMock);
        PixelView[][] pixels = new PixelView[2][2];
        imageSubject.notifyObservers(pixels);
        Mockito.verify(observerMock, Mockito.times(1)).update(pixels);
    }

    @Test
    public void testNotifyObserversMultipleObservers() {
        ObserverImage observerMock1 = Mockito.mock(ObserverImage.class);
        ObserverImage observerMock2 = Mockito.mock(ObserverImage.class);
        imageSubject.registerObserver(observerMock1);
        imageSubject.registerObserver(observerMock2);
        PixelView[][] pixels = new PixelView[2][2];
        imageSubject.notifyObservers(pixels);
        Mockito.verify(observerMock1, Mockito.times(1)).update(pixels);
        Mockito.verify(observerMock2, Mockito.times(1)).update(pixels);
    }

    @Test
    public void testNotifyObserversEmptyList() {
        PixelView[][] pixels = new PixelView[2][2];
        imageSubject.notifyObservers(pixels);
        Assertions.assertTrue(imageSubject.observerImages.isEmpty());
    }

    @Test
    public void testRegisterSameObserverMultipleTimes() {
        ObserverImage observerMock = Mockito.mock(ObserverImage.class);
        imageSubject.registerObserver(observerMock);
        imageSubject.registerObserver(observerMock);
        Assertions.assertEquals(2, imageSubject.observerImages.size());
    }
}
