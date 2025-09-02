package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.LanguageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class LanguageControllerTest {

    @Mock
    private LanguageModel languageModelMock;

    private LanguageController languageController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Field instanceField = LanguageController.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try (MockedStatic<LanguageModel> mockedStaticLanguage = mockStatic(LanguageModel.class)) {
            mockedStaticLanguage.when(LanguageModel::getInstance).thenReturn(languageModelMock);
            languageController = LanguageController.getInstance();
        }
    }

    @Test
    public void constructor() {
        LanguageController languageController1 = new LanguageController();
        Assertions.assertNotNull(languageController1);
    }

    @Test
    public void testInstanceNull() {
        LanguageController languageController1 = LanguageController.getInstance();
        Assertions.assertNotNull(languageController1);
    }

    @Test
    public void testGetInstance() {
        try (MockedStatic<LanguageController> mockedStatic = mockStatic(LanguageController.class)) {
            LanguageController instanceMock = mock(LanguageController.class);
            mockedStatic.when(LanguageController::getInstance).thenReturn(instanceMock);

            LanguageController instance1 = LanguageController.getInstance();
            LanguageController instance2 = LanguageController.getInstance();

            assertSame(instance1, instance2, "getInstance() should return the same mocked instance.");
        }
    }

    @Test
    public void testGetLanguageModel() {
        assertEquals(languageModelMock, languageController.getLanguageModel(),
                "getLanguageModel() should return the mocked LanguageModel instance.");
    }

    @Test
    public void testGetResourceBundle() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);
        when(languageModelMock.getResourceBundle()).thenReturn(mockBundle);

        ResourceBundle result = languageController.getResourceBundle();

        assertSame(mockBundle, result, "getResourceBundle() should return the mocked ResourceBundle instance.");
        verify(languageModelMock,times(1)).getResourceBundle();
    }

    @Test
    public void testSetLanguageTagInsidePreferences() {
        String languageTag = "en-US";

        languageController.setLanguageTagInsidePreferences(languageTag);

        verify(languageModelMock,times(1)).setLanguageTagInsidePreferences(languageTag);
    }
}
