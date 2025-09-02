package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.LoadImageModel;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageViewInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoadImageControllerTest {

    @Mock
    private LoadImageModel loadImageModelMock;

    @Mock
    private LanguageController languageControllerMock;

    @Mock
    private DisplayPNMImageViewInterface displayPNMImageViewMock;

    private LoadImageController loadImageController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Field instanceField = LoadImageController.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<LoadImageModel> mockedStaticLoadImage = mockStatic(LoadImageModel.class);
             MockedStatic<LanguageController> mockedStaticLanguage = mockStatic(LanguageController.class)) {
            mockedStaticLoadImage.when(LoadImageModel::getInstance).thenReturn(loadImageModelMock);
            mockedStaticLanguage.when(LanguageController::getInstance).thenReturn(languageControllerMock);

            ResourceBundle mockBundle = mock(ResourceBundle.class);
            when(languageControllerMock.getResourceBundle()).thenReturn(mockBundle);

            loadImageController = LoadImageController.getInstance();
        }
    }

    @Test
    public void constructor() {
        LoadImageController loadImageController1 = new LoadImageController();
        Assertions.assertNotNull(loadImageController1);
    }

    @Test
    public void testInstanceNull() {
        LoadImageController loadImageController1 = LoadImageController.getInstance();
        Assertions.assertNotNull(loadImageController1);
    }
    @Test
    public void testGetInstance() {
        try (MockedStatic<LoadImageController> mockedStatic = mockStatic(LoadImageController.class)) {
            LoadImageController instanceMock = mock(LoadImageController.class);
            mockedStatic.when(LoadImageController::getInstance).thenReturn(instanceMock);

            LoadImageController instance1 = LoadImageController.getInstance();
            LoadImageController instance2 = LoadImageController.getInstance();

            assertSame(instance1, instance2, "getInstance() should return the same mocked instance.");
        }
    }

    @Test
    public void testGetLoadImageModel() {
        assertEquals(loadImageModelMock, loadImageController.getLoadImageModel(),
                "getLoadImageModel() should return the mocked LoadImageModel instance.");
    }

    @Test
    public void testLoadImage_Success() {
        File mockFile = mock(File.class);
        when(displayPNMImageViewMock.showAndGetFilePath()).thenReturn(mockFile);
        when(mockFile.getAbsolutePath()).thenReturn("/mock/path/to/image.pnm");
        when(loadImageModelMock.readImage("/mock/path/to/image.pnm")).thenReturn(true);

        loadImageController.setDisplayPNMImageView(displayPNMImageViewMock);
        boolean result = loadImageController.loadImage();

        assertTrue(result, "loadImage() should return true when the image is successfully loaded.");
        verify(displayPNMImageViewMock, times(1)).showAndGetFilePath();
        verify(loadImageModelMock, times(1)).readImage("/mock/path/to/image.pnm");
    }

    @Test
    public void testLoadImage_NoFileSelected() {
        when(displayPNMImageViewMock.showAndGetFilePath()).thenReturn(null);

        loadImageController.setDisplayPNMImageView(displayPNMImageViewMock);
        boolean result = loadImageController.loadImage();

        assertTrue(result, "loadImage() should return true when no file is selected.");
        verify(displayPNMImageViewMock, times(1)).showAndGetFilePath();
        verify(loadImageModelMock, never()).readImage(anyString());
    }

    @Test
    public void testSetDisplayPNMImageView() {
        loadImageController.setDisplayPNMImageView(displayPNMImageViewMock);
        assertSame(displayPNMImageViewMock, loadImageController.getDisplayPNMImageView(),
                "setDisplayPNMImageView() should set the display view instance correctly.");
    }
    @Test
    public void testGetDisplayPNMImageView(){
        loadImageController.setDisplayPNMImageView(displayPNMImageViewMock);
        assertEquals(displayPNMImageViewMock, loadImageController.getDisplayPNMImageView(),
                "getLanguageModel() should return the mocked LanguageModel instance.");
    }
}
