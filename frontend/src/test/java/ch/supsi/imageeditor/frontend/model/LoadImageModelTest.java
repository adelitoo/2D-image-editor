package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.LoadImagePNMController;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import ch.supsi.imageeditor.frontend.observer.ObserverInfoBar;
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

public class LoadImageModelTest {

    @Mock
    private LoadImagePNMController loadImagePNMControllerMock;

    @Mock
    private ResourceBundle resourceBundleMock;

    @Mock
    private ObserverInfoBar observerInfoBarMock;

    private LoadImageModel loadImageModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try (MockedStatic<LoadImagePNMController> mockedStatic = mockStatic(LoadImagePNMController.class)) {
            mockedStatic.when(LoadImagePNMController::getInstance).thenReturn(loadImagePNMControllerMock);
            Field instanceField = LoadImageModel.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
            loadImageModel = LoadImageModel.getInstance();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Error resetting LoadImageModel singleton: " + e.getMessage());
        }
    }

    @Test
    public void constructor() {
        LoadImageModel loadImageModel1 = new LoadImageModel();
        Assertions.assertNotNull(loadImageModel1);
    }

    @Test
    public void testInstanceNull() {
        LoadImageModel loadImageModel1 = LoadImageModel.getInstance();
        Assertions.assertNotNull(loadImageModel1);
    }

    @Test
    public void testGetInstance() {
        LoadImageModel instance1 = LoadImageModel.getInstance();
        LoadImageModel instance2 = LoadImageModel.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testReadImage_Success() {
        String filePath = "test.pnm";
        Pixel[][] mockPixels = new Pixel[][]{
                {new Pixel(255, 0, 0), new Pixel(0, 255, 0)},
                {new Pixel(0, 0, 255), new Pixel(255, 255, 255)}
        };

        when(loadImagePNMControllerMock.readImage(filePath)).thenReturn(mockPixels);
        when(resourceBundleMock.getString("label.imageLoaded")).thenReturn("Image loaded: ");

        loadImageModel.setResourceBundle(resourceBundleMock);
        loadImageModel.registerInfoBarObserver(observerInfoBarMock);

        boolean result = loadImageModel.readImage(filePath);

        assertFalse(result, "readImage() should return false on success.");

        verify(loadImagePNMControllerMock, times(1)).readImage(filePath);
        verify(observerInfoBarMock, times(1)).update("Image loaded: " + filePath);
    }

    @Test
    public void testReadImage_Failure() {
        String filePath = "test.pnm";
        String errorMessage = "Invalid file";

        when(loadImagePNMControllerMock.readImage(filePath)).thenThrow(new IllegalArgumentException(errorMessage));

        loadImageModel.setResourceBundle(resourceBundleMock);
        loadImageModel.registerInfoBarObserver(observerInfoBarMock);

        boolean result = loadImageModel.readImage(filePath);

        assertTrue(result, "readImage() should return true on failure.");

        verify(loadImagePNMControllerMock, times(1)).readImage(filePath);
        verify(observerInfoBarMock, times(1)).update(errorMessage + ": " + filePath);
    }

    @Test
    public void testConvertToPixelView() {
        Pixel[][] mockPixels = new Pixel[][]{
                {new Pixel(255, 0, 0), new Pixel(0, 255, 0)},
                {new Pixel(0, 0, 255), new Pixel(255, 255, 255)}
        };

        PixelView[][] pixelViews = loadImageModel.convertToPixelView(mockPixels);

        assertNotNull(pixelViews, "convertToPixelView() should return a non-null result.");
        assertEquals(2, pixelViews.length, "PixelView array should have the correct height.");
        assertEquals(2, pixelViews[0].length, "PixelView array should have the correct width.");
        assertEquals(255, pixelViews[0][0].red(), "PixelView red value should match the original Pixel.");
        assertEquals(255, pixelViews[1][1].blue(), "PixelView blue value should match the original Pixel.");
    }

    @Test
    public void testConvertToPixelView_NullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                loadImageModel.convertToPixelView(null), "convertToPixelView() should throw IllegalArgumentException for null input.");

        assertEquals("Cannot read the image", exception.getMessage(), "Exception message should match.");
    }

    @Test
    public void testSetResourceBundle() {
        loadImageModel.setResourceBundle(resourceBundleMock);
        assertNotNull(resourceBundleMock, "ResourceBundle should be set.");
    }

    @Test
    public void testRegisterInfoBarObserver() {
        loadImageModel.registerInfoBarObserver(observerInfoBarMock);
        loadImageModel.setResourceBundle(resourceBundleMock);
        when(resourceBundleMock.getString("label.imageLoaded")).thenReturn("Image loaded: ");

        loadImageModel.readImage("test.pnm");

        verify(observerInfoBarMock, atLeastOnce()).update(anyString());
    }
}
