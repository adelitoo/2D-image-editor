package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.SaveImageModel;
import ch.supsi.imageeditor.frontend.view.SavePNMImageViewInterface;
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

public class SaveImageControllerTest {

    @Mock
    private SaveImageModel saveImageModelMock;

    @Mock
    private SavePNMImageViewInterface savePNMImageViewMock;

    private SaveImageController saveImageController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Field instanceField = SaveImageController.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<SaveImageModel> mockedStaticSaveModel = mockStatic(SaveImageModel.class)) {
            mockedStaticSaveModel.when(SaveImageModel::getInstance).thenReturn(saveImageModelMock);

            saveImageController = SaveImageController.getInstance();
        }
    }
    @Test
    public void constructor() {
        SaveImageController saveImageController1 = new SaveImageController();
        Assertions.assertNotNull(saveImageController1);
    }

    @Test
    public void testInstanceNull() {
        SaveImageController saveImageController1 = SaveImageController.getInstance();
        Assertions.assertNotNull(saveImageController1);
    }

    @Test
    public void testGetInstance() {
        try (MockedStatic<SaveImageController> mockedStatic = mockStatic(SaveImageController.class)) {
            SaveImageController instanceMock = mock(SaveImageController.class);
            mockedStatic.when(SaveImageController::getInstance).thenReturn(instanceMock);

            SaveImageController instance1 = SaveImageController.getInstance();
            SaveImageController instance2 = SaveImageController.getInstance();

            assertSame(instance1, instance2, "getInstance() should return the same mocked instance.");
        }
    }

    @Test
    public void testGetSaveImageModel() {
        assertSame(saveImageModelMock, saveImageController.getSaveImageModel(),
                "getSaveImageModel() should return the mocked SaveImageModel instance.");
    }

    @Test
    public void testSaveAsImage_WithValidFile() {
        File mockFile = mock(File.class);
        when(savePNMImageViewMock.showSavedPath()).thenReturn(mockFile);

        Field savePNMImageViewField;
        try {
            savePNMImageViewField = SaveImageController.class.getDeclaredField("savePNMImageView");
            savePNMImageViewField.setAccessible(true);
            savePNMImageViewField.set(saveImageController, savePNMImageViewMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        saveImageController.saveAsImage();

        verify(savePNMImageViewMock, times(1)).showSavedPath();
        verify(saveImageModelMock, times(1)).saveAsImage(mockFile);
    }

    @Test
    public void testSaveAsImage_NoFile() {
        when(savePNMImageViewMock.showSavedPath()).thenReturn(null);

        Field savePNMImageViewField;
        try {
            savePNMImageViewField = SaveImageController.class.getDeclaredField("savePNMImageView");
            savePNMImageViewField.setAccessible(true);
            savePNMImageViewField.set(saveImageController, savePNMImageViewMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        saveImageController.saveAsImage();

        verify(savePNMImageViewMock, times(1)).showSavedPath();
        verify(saveImageModelMock, never()).saveAsImage(any());
    }

    @Test
    public void testSetBundle() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        saveImageController.setBundle(mockBundle);

        verify(saveImageModelMock, times(1)).setBundle(mockBundle);
    }

    @Test
    public void testSaveImage() {
        saveImageController.saveImage();

        verify(saveImageModelMock, times(1)).saveImage();
    }
}
