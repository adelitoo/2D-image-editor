package ch.supsi.imageeditor.frontend.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class AboutSubjectTest {
    private AboutSubject aboutSubject;

    @BeforeEach
    public void setup() {
        aboutSubject = new AboutSubject();
    }

    @Test
    public void testRegisterObserver() {
        ObserverAbout observerMock = Mockito.mock(ObserverAbout.class);

        aboutSubject.registerObserver(observerMock);

        Assertions.assertEquals(1, aboutSubject.observerAbouts.size(), "La lista degli osservatori deve contenere un elemento.");
        Assertions.assertTrue(aboutSubject.observerAbouts.contains(observerMock), "L'osservatore registrato deve essere presente nella lista.");
    }

    @Test
    public void testNotifyObserversSingleObserver() {
        ObserverAbout observerMock = Mockito.mock(ObserverAbout.class);

        aboutSubject.registerObserver(observerMock);

        String name = "Image Editor";
        String developer = "Developer Team";
        String version = "1.0.0";
        String date = "2024-11-28";

        aboutSubject.notifyObservers(name, developer, version, date);

        Mockito.verify(observerMock, Mockito.times(1)).update(name, developer, version, date);
    }

    @Test
    public void testNotifyObserversMultipleObservers() {
        ObserverAbout observerMock1 = Mockito.mock(ObserverAbout.class);
        ObserverAbout observerMock2 = Mockito.mock(ObserverAbout.class);

        aboutSubject.registerObserver(observerMock1);
        aboutSubject.registerObserver(observerMock2);

        String name = "Image Editor";
        String developer = "Developer Team";
        String version = "1.0.0";
        String date = "2024-11-28";

        aboutSubject.notifyObservers(name, developer, version, date);

        Mockito.verify(observerMock1, Mockito.times(1)).update(name, developer, version, date);
        Mockito.verify(observerMock2, Mockito.times(1)).update(name, developer, version, date);
    }

    @Test
    public void testNotifyObserversEmptyList() {
        String name = "Image Editor";
        String developer = "Developer Team";
        String version = "1.0.0";
        String date = "2024-11-28";

        aboutSubject.notifyObservers(name, developer, version, date);

        Assertions.assertTrue(aboutSubject.observerAbouts.isEmpty(), "La lista degli osservatori deve essere vuota.");
    }

    @Test
    public void testRegisterSameObserverMultipleTimes() {
        ObserverAbout observerMock = Mockito.mock(ObserverAbout.class);

        aboutSubject.registerObserver(observerMock);
        aboutSubject.registerObserver(observerMock);

        Assertions.assertEquals(2, aboutSubject.observerAbouts.size(), "La lista degli osservatori dovrebbe contenere due elementi.");
    }
}
