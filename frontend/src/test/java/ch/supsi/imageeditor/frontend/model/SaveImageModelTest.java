package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.SaveImagePNMController;
import ch.supsi.imageeditor.frontend.observer.ObserverInfoBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class SaveImageModelTest {

    @Mock
    private SaveImagePNMController saveImagePNMControllerMock;

    @Mock
    private ResourceBundle resourceBundleMock;

    @Mock
    private ObserverInfoBar observerInfoBarMock;

    private SaveImageModel saveImageModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try (MockedStatic<SaveImagePNMController> mockedStatic = mockStatic(SaveImagePNMController.class)) {
            mockedStatic.when(SaveImagePNMController::getInstance).thenReturn(saveImagePNMControllerMock);

            Field instanceField = SaveImageModel.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);

            saveImageModel = SaveImageModel.getInstance();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error resetting SaveImageModel singleton: " + e.getMessage());
        }
    }

    @Test
    public void constructor() {
        SaveImageModel saveImageModel1 = new SaveImageModel();
        Assertions.assertNotNull(saveImageModel1);
    }

    @Test
    public void testInstanceNull() {
        SaveImageModel saveImageModel1 = SaveImageModel.getInstance();
        Assertions.assertNotNull(saveImageModel1);
    }

    @Test
    public void testGetInstance() {
        SaveImageModel instance1 = SaveImageModel.getInstance();
        SaveImageModel instance2 = SaveImageModel.getInstance();

        assertSame(instance1, instance2, "getInstance() deve restituire sempre la stessa istanza.");
    }

    @Test
    public void testSaveImage() {
        when(resourceBundleMock.getString("label.clickSaveButton")).thenReturn("Save button clicked");

        saveImageModel.setBundle(resourceBundleMock);
        saveImageModel.registerObserver(observerInfoBarMock);

        saveImageModel.saveImage();

        verify(saveImagePNMControllerMock, times(1)).saveImage();
        verify(observerInfoBarMock, times(1)).update("Save button clicked");
    }

    @Test
    public void testSaveAsImage() {
        File mockFile = mock(File.class);
        String mockFilePath = "path/to/file.pnm";

        when(mockFile.getAbsolutePath()).thenReturn(mockFilePath);
        when(resourceBundleMock.getString("label.clickSaveButton")).thenReturn("Save button clicked");

        saveImageModel.setBundle(resourceBundleMock);
        saveImageModel.registerObserver(observerInfoBarMock);

        saveImageModel.saveAsImage(mockFile);

        verify(saveImagePNMControllerMock, times(1)).saveAsImage(mockFilePath);
        verify(observerInfoBarMock, times(1)).update("Save button clicked");
    }

    @Test
    public void testSetBundle() {
        saveImageModel.setBundle(resourceBundleMock);

        Field bundleField;
        try {
            bundleField = SaveImageModel.class.getDeclaredField("bundle");
            bundleField.setAccessible(true);
            ResourceBundle actualBundle = (ResourceBundle) bundleField.get(saveImageModel);

            assertSame(resourceBundleMock, actualBundle, "Il bundle dovrebbe essere impostato correttamente.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Errore durante l'accesso al campo bundle: " + e.getMessage());
        }
    }
}
