package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.TransformationsController;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageView;
import ch.supsi.imageeditor.frontend.view.InfoBarView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransformationModelTest {

    @Mock
    private TransformationsController transformationsControllerMock;

    @Mock
    private ResourceBundle resourceBundleMock;

    @Mock
    private DisplayPNMImageView displayPNMImageViewMock;

    @Mock
    private InfoBarView infoBarViewMock;

    private TransformationModel transformationModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try (MockedStatic<TransformationsController> mockedStatic = mockStatic(TransformationsController.class)) {
            mockedStatic.when(TransformationsController::getInstance).thenReturn(transformationsControllerMock);

            Field instanceField = TransformationModel.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);

            transformationModel = TransformationModel.getInstance();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error resetting TransformationModel singleton: " + e.getMessage());
        }
    }

    @Test
    public void constructor() {
        TransformationModel transformationsModel = new TransformationModel();
        Assertions.assertNotNull(transformationsModel);
    }

    @Test
    public void testInstanceNull() {
        TransformationModel transformationsModel = TransformationModel.getInstance();
        Assertions.assertNotNull(transformationsModel);
    }
    @Test
    public void testGetInstance() {
        TransformationModel instance1 = TransformationModel.getInstance();
        TransformationModel instance2 = TransformationModel.getInstance();

        assertSame(instance1, instance2, "getInstance() deve restituire sempre la stessa istanza.");
    }

    @Test
    public void testAddTransformationIfSelectedLabelIsNotNull() {
        List<String> expectedTransformations = new ArrayList<>();
        String transformation = "rotate";

        transformationModel.registerObserver(list -> {
            assertEquals(expectedTransformations, list, "Lista delle trasformazioni notificata.");
        });

        expectedTransformations.add(transformation);
        transformationModel.addTransformation(transformation);
    }
    @Test
    public void testAddTransformationIfSelectedLabelIsNull() {
        List<String> expectedTransformations = new ArrayList<>();
        String transformation = null;

        transformationModel.registerObserver(list -> {
            assertEquals(expectedTransformations, list, "Lista delle trasformazioni notificata.");
        });
        transformationModel.addTransformation(transformation);
        try {
            Field listField = TransformationModel.class.getDeclaredField("listOfTransformations");
            listField.setAccessible(true);
            List<String> internalList = (List<String>) listField.get(transformationModel);
            assertEquals(0, internalList.size(), "La lista non dovrebbe contenere alcuna trasformazione.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAddTransformationWhenListThrowsIllegalArgumentException() {
        List mockedList = mock(List.class);
        try {
            Field listField = TransformationModel.class.getDeclaredField("listOfTransformations");
            listField.setAccessible(true);
            assertEquals(0, mockedList.size(), "La lista non dovrebbe contenere alcuna trasformazione.");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String transformation = "rotate";

        doThrow(new IllegalArgumentException("Unknown transformation selected")).when(mockedList).add(anyString());

        PrintStream originalSystemOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        transformationModel.addTransformation(transformation);

        String expectedMessage = "Unknown transformation selected: rotate";
        assertFalse(baos.toString().contains(expectedMessage), "Il messaggio di errore non è stato stampato correttamente.");

        System.setOut(originalSystemOut);
    }

    @Test
    public void testClearPipelineListView() {
        transformationModel.addTransformation("rotate");
        transformationModel.clearPipelineListView();

        transformationModel.registerObserver(list -> assertSame(new ArrayList<>(), list, "Lista delle trasformazioni dovrebbe essere vuota."));
    }

    @Test
    public void testApplyTransformations() {
        String transformation1 = "rotate";
        String transformation2 = "invert";

        Pixel[][] mockPixels = new Pixel[][]{
                {new Pixel(0, 0, 0), new Pixel(255, 255, 255)},
                {new Pixel(128, 128, 128), new Pixel(64, 64, 64)}
        };

        when(transformationsControllerMock.applyTransformations(anyList())).thenReturn(mockPixels);
        when(resourceBundleMock.getString("label.transformationCompleted")).thenReturn("Transformation completed");

        transformationModel.setResourceBundle(resourceBundleMock);
        transformationModel.setObserverImageDisplay(displayPNMImageViewMock);
        transformationModel.setObserverInfoBar(infoBarViewMock);

        transformationModel.addTransformation(transformation1);
        transformationModel.addTransformation(transformation2);

        transformationModel.applyTransformations();

        verify(transformationsControllerMock, times(1)).applyTransformations(anyList());
        verify(displayPNMImageViewMock, times(1)).update(any(PixelView[][].class));
        verify(infoBarViewMock, times(1)).update("Transformation completed");
    }

    @Test
    public void testSetObserverImageDisplay() {
        transformationModel.setObserverImageDisplay(displayPNMImageViewMock);

        transformationModel.addTransformation("rotate");

        verify(displayPNMImageViewMock, never()).update(any());
    }

    @Test
    public void testSetObserverInfoBar() {
        Pixel[][] mockPixels = new Pixel[][]{
                {new Pixel(0, 0, 0), new Pixel(255, 255, 255)},
                {new Pixel(128, 128, 128), new Pixel(64, 64, 64)}
        };

        when(resourceBundleMock.getString("label.transformationCompleted")).thenReturn("Transformation completed");
        when(transformationsControllerMock.applyTransformations(anyList())).thenReturn(mockPixels);  // Mock dei pixel trasformati

        transformationModel.setObserverInfoBar(infoBarViewMock);
        transformationModel.setResourceBundle(resourceBundleMock);

        transformationModel.applyTransformations();

        //verify(infoBarViewMock, times(1)).update("Transformation completed");

        verify(transformationsControllerMock, times(1)).applyTransformations(anyList());
    }


    @Test
    public void testSetBundle() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        transformationModel.setResourceBundle(mockBundle);

        assertSame(mockBundle, transformationModel.getResourceBundle(), "Il bundle dovrebbe essere impostato correttamente.");
    }
}
