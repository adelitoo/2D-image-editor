package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.PreferencesModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PreferencesControllerTest {

    @Mock
    private PreferencesModel preferencesModelMock;

    private PreferencesController preferencesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Field instanceField;
        try {
            instanceField = PreferencesController.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<PreferencesModel> mockedStatic = mockStatic(PreferencesModel.class)) {
            mockedStatic.when(PreferencesModel::getInstance).thenReturn(preferencesModelMock);
            preferencesController = PreferencesController.getInstance();
        }
    }

    @Test
    public void constructor() {
        PreferencesController preferencesController1 = new PreferencesController();
        Assertions.assertNotNull(preferencesController1);
    }

    @Test
    public void testInstanceNull() {
        PreferencesController preferencesController1 = PreferencesController.getInstance();
        Assertions.assertNotNull(preferencesController1);
    }

    @Test
    public void testGetInstance() {
        PreferencesController instance1 = PreferencesController.getInstance();
        PreferencesController instance2 = PreferencesController.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testGetPreference() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);
        when(preferencesModelMock.getPreference()).thenReturn(mockBundle);

        ResourceBundle result = preferencesController.getPreference();

        assertSame(mockBundle, result, "getPreference() should return the same ResourceBundle.");
        verify(preferencesModelMock, times(1)).getPreference();
    }

    @Test
    public void testSetPreference() {
        String tag = "language";
        String preferenceTag = "en";

        preferencesController.setPreference(tag, preferenceTag);

        verify(preferencesModelMock, times(1)).setPreference(tag, preferenceTag);
    }
}
