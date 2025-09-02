package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.transformationsOptions.Transformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class TransformationsModelTest {

    @Mock
    private LoadImagePNMModel loadImagePNMModelMock;

    @Mock
    private Transformation transformationMock;

    @Mock
    private PNMObject pnmObjectMock;

    private TransformationsModel transformationsModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Field instanceField = TransformationsModel.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<LoadImagePNMModel> mockedStatic = mockStatic(LoadImagePNMModel.class)) {
            mockedStatic.when(LoadImagePNMModel::getInstance).thenReturn(loadImagePNMModelMock);
            transformationsModel = TransformationsModel.getInstance();
        }
    }

    @Test
    public void testSingleton() {
        TransformationsModel instance1 = TransformationsModel.getInstance();
        TransformationsModel instance2 = TransformationsModel.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance.");
    }

    @Test
    public void testApplyTransformations() {
        List<Transformation> transformations = Arrays.asList(transformationMock, transformationMock);

        transformationsModel.applyTransformations(transformations, pnmObjectMock);

        verify(transformationMock, times(2)).doTransformation(pnmObjectMock);

        verify(loadImagePNMModelMock, times(1)).setLoadObj(pnmObjectMock);
    }

    @Test
    public void testApplyTransformationsWithEmptyList() {
        List<Transformation> transformations = List.of();

        transformationsModel.applyTransformations(transformations, pnmObjectMock);

        verify(transformationMock, times(0)).doTransformation(pnmObjectMock);

        verify(loadImagePNMModelMock, times(1)).setLoadObj(pnmObjectMock);
    }

    @Test
    public void testApplyTransformationsWithNullList() {
        transformationsModel.applyTransformations(null, pnmObjectMock);

        verify(loadImagePNMModelMock, times(1)).setLoadObj(pnmObjectMock);
    }
}
