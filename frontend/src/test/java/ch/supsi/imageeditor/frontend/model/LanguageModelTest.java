package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.PreferencesController;
import ch.supsi.imageeditor.backend.application.PreferencesControllerInterface;
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

public class LanguageModelTest {

    @Mock
    private PreferencesController preferencesControllerMock;

    @Mock
    private ResourceBundle resourceBundleMock;

    private LanguageModel languageModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        try (MockedStatic<PreferencesController> mockedStatic = mockStatic(PreferencesController.class)) {
            mockedStatic.when(PreferencesController::getInstance).thenReturn(preferencesControllerMock);
            Field instanceField = LanguageModel.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
            languageModel = LanguageModel.getInstance();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Error resetting LanguageModel singleton: " + e.getMessage());
        }
    }

    @Test
    public void constructor() {
        LanguageModel languageModel1 = new LanguageModel();
        Assertions.assertNotNull(languageModel1);
    }

    @Test
    public void testInstanceNull() {
        LanguageModel languageModel1 = LanguageModel.getInstance();
        Assertions.assertNotNull(languageModel1);
    }

    @Test
    public void testGetInstance() {
        LanguageModel instance1 = LanguageModel.getInstance();
        LanguageModel instance2 = LanguageModel.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testGetResourceBundle() {
        when(preferencesControllerMock.getPreference()).thenReturn(resourceBundleMock);
        ResourceBundle result = languageModel.getResourceBundle();
        assertSame(resourceBundleMock, result, "getResourceBundle() should return the mocked ResourceBundle.");
    }

    @Test
    public void testSetLanguageTagInsidePreferences() {
        String languageTag = "en-US";
        String expectedNotification = "Language button clicked";

        when(preferencesControllerMock.getPreference()).thenReturn(resourceBundleMock);
        when(resourceBundleMock.getString("label.clickLanguageButton")).thenReturn(expectedNotification);

        languageModel.setLanguageTagInsidePreferences(languageTag);

        verify(preferencesControllerMock, times(1)).setPreference("language-tag", languageTag);

        String actualNotification = resourceBundleMock.getString("label.clickLanguageButton");
        assertEquals(expectedNotification, actualNotification, "La notifica generata non corrisponde.");

    }

}
