package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.LoadImagePNMModel;
import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoadImagePNMControllerTest {

    @Mock
    private LoadImagePNMModel loadImagePNMModelMock;

    private LoadImagePNMController loadImagePNMController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Field instanceField;
        try {
            instanceField = LoadImagePNMController.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<LoadImagePNMModel> mockedStatic = mockStatic(LoadImagePNMModel.class)) {
            mockedStatic.when(LoadImagePNMModel::getInstance).thenReturn(loadImagePNMModelMock);
            loadImagePNMController = LoadImagePNMController.getInstance();
        }
    }

    @Test
    public void constructor() {
        LoadImagePNMController loadImagePNMController1 = new LoadImagePNMController();
        Assertions.assertNotNull(loadImagePNMController1);
    }

    @Test
    public void testInstanceNull() {
        LoadImagePNMController loadImagePNMController1 = LoadImagePNMController.getInstance();
        Assertions.assertNotNull(loadImagePNMController1);
    }
    @Test
    public void testGetInstance() {
        LoadImagePNMController instance1 = LoadImagePNMController.getInstance();
        LoadImagePNMController instance2 = LoadImagePNMController.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testReadImage() {
        String filePath = "test.pnm";
        Pixel[][] mockPixels = new Pixel[2][2];
        when(loadImagePNMModelMock.readImage(filePath)).thenReturn(mockPixels);

        Pixel[][] result = loadImagePNMController.readImage(filePath);

        assertSame(mockPixels, result, "readImage() should return the same mock data.");
        verify(loadImagePNMModelMock, times(1)).readImage(filePath);
    }

    @Test
    public void testGetLoadObj() {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(loadImagePNMModelMock.getLoadObj()).thenReturn(mockPNMObject);

        PNMObject result = loadImagePNMController.getLoadObj();

        assertSame(mockPNMObject, result, "getLoadObj() should return the same mock object.");
        verify(loadImagePNMModelMock, times(1)).getLoadObj();
    }
}
