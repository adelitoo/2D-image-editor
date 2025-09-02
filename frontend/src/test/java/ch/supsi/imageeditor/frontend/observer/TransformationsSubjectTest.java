package ch.supsi.imageeditor.frontend.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class TransformationsSubjectTest {
    private TransformationsSubject transformationsSubject;

    @BeforeEach
    public void setup() {
        transformationsSubject = new TransformationsSubject();
    }

    @Test
    public void testRegisterObserver() {
        ObserverTransformations observerMock = Mockito.mock(ObserverTransformations.class);
        transformationsSubject.registerObserver(observerMock);
        Assertions.assertEquals(1, transformationsSubject.observerTransformations.size());
        Assertions.assertTrue(transformationsSubject.observerTransformations.contains(observerMock));
    }

    @Test
    public void testNotifyObserversSingleObserver() {
        ObserverTransformations observerMock = Mockito.mock(ObserverTransformations.class);
        transformationsSubject.registerObserver(observerMock);
        List<String> transformations = Arrays.asList("Rotation", "Scaling");
        transformationsSubject.notifyObservers(transformations);
        Mockito.verify(observerMock, Mockito.times(1)).update(transformations);
    }

    @Test
    public void testNotifyObserversMultipleObservers() {
        ObserverTransformations observerMock1 = Mockito.mock(ObserverTransformations.class);
        ObserverTransformations observerMock2 = Mockito.mock(ObserverTransformations.class);
        transformationsSubject.registerObserver(observerMock1);
        transformationsSubject.registerObserver(observerMock2);
        List<String> transformations = Arrays.asList("Translation", "Shearing");
        transformationsSubject.notifyObservers(transformations);
        Mockito.verify(observerMock1, Mockito.times(1)).update(transformations);
        Mockito.verify(observerMock2, Mockito.times(1)).update(transformations);
    }

    @Test
    public void testNotifyObserversEmptyList() {
        List<String> transformations = Arrays.asList("Reflection", "Cropping");
        transformationsSubject.notifyObservers(transformations);
        Assertions.assertTrue(transformationsSubject.observerTransformations.isEmpty());
    }

    @Test
    public void testRegisterSameObserverMultipleTimes() {
        ObserverTransformations observerMock = Mockito.mock(ObserverTransformations.class);
        transformationsSubject.registerObserver(observerMock);
        transformationsSubject.registerObserver(observerMock);
        Assertions.assertEquals(2, transformationsSubject.observerTransformations.size());
    }
}
