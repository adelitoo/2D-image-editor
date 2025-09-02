package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.TransformationModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class TransformationControllerTest {

    @Mock
    private TransformationModel transformationModelMock;

    @Mock
    private LanguageController languageControllerMock;

    private TransformationController transformationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Field instanceField = TransformationController.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<TransformationModel> mockedStaticTransformationModel = mockStatic(TransformationModel.class);
             MockedStatic<LanguageController> mockedStaticLanguageController = mockStatic(LanguageController.class)) {

            mockedStaticTransformationModel.when(TransformationModel::getInstance).thenReturn(transformationModelMock);
            mockedStaticLanguageController.when(LanguageController::getInstance).thenReturn(languageControllerMock);

            transformationController = TransformationController.getInstance();
        }
    }
    @Test
    public void constructor() {
        TransformationController transformationController1 = new TransformationController();
        Assertions.assertNotNull(transformationController1);
    }

    @Test
    public void testInstanceNull() {
        TransformationController transformationController1 = TransformationController.getInstance();
        Assertions.assertNotNull(transformationController1);
    }

    @Test
    public void testGetInstance() {
        try (MockedStatic<TransformationController> mockedStatic = mockStatic(TransformationController.class)) {
            TransformationController instanceMock = mock(TransformationController.class);
            mockedStatic.when(TransformationController::getInstance).thenReturn(instanceMock);

            TransformationController instance1 = TransformationController.getInstance();
            TransformationController instance2 = TransformationController.getInstance();

            assertSame(instance1, instance2, "getInstance() should return the same mocked instance.");
        }
    }

    @Test
    public void testAddTransformationToPipeline() {
        String transformation = "Negative";

        transformationController.addTransformationToPipeline(transformation);

        verify(transformationModelMock, times(1)).addTransformation(transformation);
    }

    @Test
    public void testGetTransformationModel() {
        assertSame(transformationModelMock, transformationController.getTransformationModel(),
                "getTransformationModel() should return the mocked TransformationModel instance.");
    }

    @Test
    public void testClearPipelineListView() {
        transformationController.clearPipelineListView();

        verify(transformationModelMock, times(1)).clearPipelineListView();
    }

    @Test
    public void testApplyTransformations() {
        transformationController.applyTransformations();

        verify(transformationModelMock, times(1)).applyTransformations();
    }
}
