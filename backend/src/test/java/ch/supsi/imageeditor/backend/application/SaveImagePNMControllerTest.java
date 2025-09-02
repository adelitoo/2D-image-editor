package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.SaveImagePNMModel;
import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class SaveImagePNMControllerTest {

    @Mock
    private SaveImagePNMModel saveImagePNMModelMock;

    @Mock
    private LoadImagePNMController loadImagePNMControllerMock;

    @Mock
    private PNMObject pnmObjectMock;

    private SaveImagePNMController saveImagePNMController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Field instanceField;
        try {
            instanceField = SaveImagePNMController.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<SaveImagePNMModel> mockedSaveStatic = mockStatic(SaveImagePNMModel.class);
             MockedStatic<LoadImagePNMController> mockedLoadStatic = mockStatic(LoadImagePNMController.class)) {
            mockedSaveStatic.when(SaveImagePNMModel::getInstance).thenReturn(saveImagePNMModelMock);
            mockedLoadStatic.when(LoadImagePNMController::getInstance).thenReturn(loadImagePNMControllerMock);
            saveImagePNMController = SaveImagePNMController.getInstance();
        }
    }

    @Test
    public void constructor() {
        SaveImagePNMController saveImagePNMController1 = new SaveImagePNMController();
        Assertions.assertNotNull(saveImagePNMController1);
    }

    @Test
    public void testInstanceNull() {
        SaveImagePNMController saveImagePNMController1 = SaveImagePNMController.getInstance();
        Assertions.assertNotNull(saveImagePNMController1);
    }
    @Test
    public void testGetInstance() {
        SaveImagePNMController instance1 = SaveImagePNMController.getInstance();
        SaveImagePNMController instance2 = SaveImagePNMController.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testSaveImage() {
        when(loadImagePNMControllerMock.getLoadObj()).thenReturn(pnmObjectMock);

        saveImagePNMController.saveImage();

        verify(loadImagePNMControllerMock, times(1)).getLoadObj();
        verify(saveImagePNMModelMock, times(1)).saveImage(pnmObjectMock);
    }

    @Test
    public void testSaveAsImage() {
        String path = "output.pnm";
        when(loadImagePNMControllerMock.getLoadObj()).thenReturn(pnmObjectMock);

        saveImagePNMController.saveAsImage(path);

        verify(loadImagePNMControllerMock, times(1)).getLoadObj();
        verify(saveImagePNMModelMock, times(1)).saveAsImage(pnmObjectMock, path);
    }
}
