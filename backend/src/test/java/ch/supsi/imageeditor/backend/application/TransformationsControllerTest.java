package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.backend.business.TransformationsModel;

import ch.supsi.imageeditor.backend.business.transformationsOptions.ConverterTransformations;
import ch.supsi.imageeditor.backend.business.transformationsOptions.FlipImageSideToSide;
import ch.supsi.imageeditor.backend.business.transformationsOptions.NegativeImageColors;
import ch.supsi.imageeditor.backend.business.transformationsOptions.Transformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class TransformationsControllerTest {

    @Mock
    private TransformationsModel transformationsModelMock;

    @Mock
    private LoadImagePNMController loadImagePNMControllerMock;

    @Mock
    private ConverterTransformations converterTransformationMock;

    @Mock
    private ResourceBundle resourceBundleMock;
    @Mock
    private PreferencesController preferencesControllerMock;

    @Mock
    private PNMObject mockPNMObject;

    private TransformationsController transformationsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Field instanceField;
        try {
            instanceField = TransformationsController.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<TransformationsModel> mockedTransformationsStatic = mockStatic(TransformationsModel.class);
             MockedStatic<LoadImagePNMController> mockedLoadImageStatic = mockStatic(LoadImagePNMController.class);
             MockedStatic<PreferencesController> mockedPreferencesControllerStatic = mockStatic(PreferencesController.class) ){

            mockedTransformationsStatic.when(TransformationsModel::getInstance).thenReturn(transformationsModelMock);
            mockedLoadImageStatic.when(LoadImagePNMController::getInstance).thenReturn(loadImagePNMControllerMock);
            mockedPreferencesControllerStatic.when(PreferencesController::getInstance).thenReturn(preferencesControllerMock);

            when(preferencesControllerMock.getPreference()).thenReturn(resourceBundleMock);
            when(loadImagePNMControllerMock.getLoadObj()).thenReturn(mockPNMObject);

            transformationsController = new TransformationsController(converterTransformationMock);
        }
    }


    @Test
    public void constructor() {
        TransformationsController transformationsController = new TransformationsController(new ConverterTransformations());
        Assertions.assertNotNull(transformationsController);
    }

    @Test
    public void testInstanceNull() {
        TransformationsController transformationsController = TransformationsController.getInstance();
        Assertions.assertNotNull(transformationsController);
    }


    @Test
    public void testGetInstance() {
        TransformationsController instance1 = TransformationsController.getInstance();
        TransformationsController instance2 = TransformationsController.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testApplyTransformations() {
        List<String> transformationsList = List.of("Flip Image (Side-to-Side)", "Negative");
        List<Transformation> transfListFinal = List.of(new FlipImageSideToSide(), new NegativeImageColors());
        Pixel[][] mockPixels = new Pixel[2][2];

        when(resourceBundleMock.getString("label.flipSideToSide")).thenReturn("Flip Image (Side-to-Side)");
        when(resourceBundleMock.getString("label.negative")).thenReturn("Negative");

        when(mockPNMObject.getPixels()).thenReturn(mockPixels);
        when(converterTransformationMock.convertList(anyList(), any(ResourceBundle.class)))
                .thenReturn(transfListFinal);

        Pixel[][] result = transformationsController.applyTransformations(transformationsList);

        verify(converterTransformationMock, times(1)).convertList(transformationsList, resourceBundleMock);
        verify(transformationsModelMock, times(1)).applyTransformations(transfListFinal, mockPNMObject);
        assertSame(mockPixels, result, "applyTransformations() should return the same pixels from the loadImagePNMController.");
    }
}
