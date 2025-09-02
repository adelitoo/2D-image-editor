package ch.supsi.imageeditor.frontend.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InfoBarSubjectTest {
    private InfoBarSubject infoBarSubject;

    @BeforeEach
    public void setup() {
        infoBarSubject = new InfoBarSubject();
    }

    @Test
    public void testRegisterObserver() {
        ObserverInfoBar observerMock = Mockito.mock(ObserverInfoBar.class);
        infoBarSubject.registerObserver(observerMock);
        Assertions.assertEquals(1, infoBarSubject.observerInfoBars.size());
        Assertions.assertTrue(infoBarSubject.observerInfoBars.contains(observerMock));
    }

    @Test
    public void testNotifyObserversSingleObserver() {
        ObserverInfoBar observerMock = Mockito.mock(ObserverInfoBar.class);
        infoBarSubject.registerObserver(observerMock);
        String message = "Test message";
        infoBarSubject.notifyObservers(message);
        Mockito.verify(observerMock, Mockito.times(1)).update(message);
    }

    @Test
    public void testNotifyObserversMultipleObservers() {
        ObserverInfoBar observerMock1 = Mockito.mock(ObserverInfoBar.class);
        ObserverInfoBar observerMock2 = Mockito.mock(ObserverInfoBar.class);
        infoBarSubject.registerObserver(observerMock1);
        infoBarSubject.registerObserver(observerMock2);
        String message = "Test message";
        infoBarSubject.notifyObservers(message);
        Mockito.verify(observerMock1, Mockito.times(1)).update(message);
        Mockito.verify(observerMock2, Mockito.times(1)).update(message);
    }

    @Test
    public void testNotifyObserversEmptyList() {
        String message = "Test message";
        infoBarSubject.notifyObservers(message);
        Assertions.assertTrue(infoBarSubject.observerInfoBars.isEmpty());
    }

    @Test
    public void testRegisterSameObserverMultipleTimes() {
        ObserverInfoBar observerMock = Mockito.mock(ObserverInfoBar.class);
        infoBarSubject.registerObserver(observerMock);
        infoBarSubject.registerObserver(observerMock);
        Assertions.assertEquals(2, infoBarSubject.observerInfoBars.size());
    }
}
