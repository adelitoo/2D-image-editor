package ch.supsi.imageeditor.backend.business.transformationsOptions;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConverterTransformationsTest {

    private ConverterTransformations converterTransformations;
    private ResourceBundle resourceBundleMock;

    @BeforeEach
    void setUp() {
        converterTransformations = new ConverterTransformations();
        resourceBundleMock = mock(ResourceBundle.class);
    }
    @Test
    public void constructor() {
        ConverterTransformations obj = new ConverterTransformations();
        Assertions.assertNotNull(obj);
    }


    @Test
    void testConvertList_RotateClockwise() {
        List<String> input = List.of("label.rotateClockwise");
        when(resourceBundleMock.getString("label.rotateClockwise")).thenReturn("label.rotateClockwise");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof RotateImgClockwise);
    }

    @Test
    void testConvertList_RotateAntiClockwise() {
        List<String> input = List.of("label.rotateAntiClockwise");
        when(resourceBundleMock.getString("label.rotateAntiClockwise")).thenReturn("label.rotateAntiClockwise");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof RotateImgAntiClockwise);
    }

    @Test
    void testConvertList_Negative() {
        List<String> input = List.of("label.negative");
        when(resourceBundleMock.getString("label.negative")).thenReturn("label.negative");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof NegativeImageColors);
    }

    @Test
    void testConvertList_FlipUpsideDown() {
        List<String> input = List.of("label.flipUpsideDown");
        when(resourceBundleMock.getString("label.flipUpsideDown")).thenReturn("label.flipUpsideDown");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof FlipImageUpsideDown);
    }

    @Test
    void testConvertList_FlipSideToSide() {
        List<String> input = List.of("label.flipSideToSide");
        when(resourceBundleMock.getString("label.flipSideToSide")).thenReturn("label.flipSideToSide");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof FlipImageSideToSide);
    }

    @Test
    void testConvertList_MultipleTransformations() {
        List<String> input = List.of("label.rotateClockwise", "label.negative");
        when(resourceBundleMock.getString("label.rotateClockwise")).thenReturn("label.rotateClockwise");
        when(resourceBundleMock.getString("label.negative")).thenReturn("label.negative");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof RotateImgClockwise);
        assertTrue(result.get(1) instanceof NegativeImageColors);
    }

    @Test
    void testConvertList_NoMatchingTransformation() {
        List<String> input = List.of("label.nonExistentTransformation");
        when(resourceBundleMock.getString("label.nonExistentTransformation")).thenReturn("label.nonExistentTransformation");

        List<Transformation> result = converterTransformations.convertList(input, resourceBundleMock);

        assertEquals(0, result.size());
    }
}
