package ch.supsi.imageeditor.frontend.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HelpSubjectTest {
    private HelpSubject helpSubject;

    @BeforeEach
    public void setup() {
        helpSubject = new HelpSubject();
    }

    @Test
    public void testRegisterObserver() {
        ObserverHelp observerMock = Mockito.mock(ObserverHelp.class);

        helpSubject.registerObserver(observerMock);

        Assertions.assertEquals(1, helpSubject.observerHelps.size(), "La lista degli osservatori deve contenere un elemento.");
        Assertions.assertTrue(helpSubject.observerHelps.contains(observerMock), "L'osservatore registrato deve essere presente nella lista.");
    }

    @Test
    public void testNotifyObserversSingleObserver() {
        ObserverHelp observerMock = Mockito.mock(ObserverHelp.class);

        helpSubject.registerObserver(observerMock);

        String title = "Help";
        String howToUse = "Click on 'Help' in the menu bar to access instructions.";
        String closeButtonText = "Close";

        helpSubject.notifyObservers(title, howToUse, closeButtonText);

        Mockito.verify(observerMock, Mockito.times(1)).update(title, howToUse, closeButtonText);
    }

    @Test
    public void testNotifyObserversMultipleObservers() {
        ObserverHelp observerMock1 = Mockito.mock(ObserverHelp.class);
        ObserverHelp observerMock2 = Mockito.mock(ObserverHelp.class);

        helpSubject.registerObserver(observerMock1);
        helpSubject.registerObserver(observerMock2);

        String title = "Help";
        String howToUse = "Click on 'Help' in the menu bar to access instructions.";
        String closeButtonText = "Close";

        helpSubject.notifyObservers(title, howToUse, closeButtonText);

        Mockito.verify(observerMock1, Mockito.times(1)).update(title, howToUse, closeButtonText);
        Mockito.verify(observerMock2, Mockito.times(1)).update(title, howToUse, closeButtonText);
    }

    @Test
    public void testNotifyObserversEmptyList() {
        String title = "Help";
        String howToUse = "Click on 'Help' in the menu bar to access instructions.";
        String closeButtonText = "Close";

        helpSubject.notifyObservers(title, howToUse, closeButtonText);

        Assertions.assertTrue(helpSubject.observerHelps.isEmpty(), "La lista degli osservatori deve essere vuota.");
    }

    @Test
    public void testRegisterSameObserverMultipleTimes() {
        ObserverHelp observerMock = Mockito.mock(ObserverHelp.class);

        helpSubject.registerObserver(observerMock);
        helpSubject.registerObserver(observerMock);

        Assertions.assertEquals(2, helpSubject.observerHelps.size(), "La lista degli osservatori dovrebbe contenere due elementi.");
    }
}
