package ch.supsi.imageeditor.backend.business.exportImages;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ExportFromPGMToPBMTest {

    @Mock
    private PNMObject pnmObjectMock;

    private ExportFromPGMToPBM exportFromPGMToPBM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        exportFromPGMToPBM = new ExportFromPGMToPBM();
    }

    @Test
    public void constructor() {
        ExportFromPGMToPBM exportFromPGMToPBM1 = new ExportFromPGMToPBM();
        Assertions.assertNotNull(exportFromPGMToPBM1);
    }

    @Test
    void testExportWithP2Header() {
        when(pnmObjectMock.getHeader()).thenReturn("P2");
        when(pnmObjectMock.getWidth()).thenReturn(2);
        when(pnmObjectMock.getHeight()).thenReturn(2);

        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(100, 100, 100); // Should be black (0)
        pixels[0][1] = new Pixel(150, 150, 150); // Should be white (255)
        pixels[1][0] = new Pixel(200, 200, 200); // Should be white (255)
        pixels[1][1] = new Pixel(50, 50, 50); // Should be black (0)

        when(pnmObjectMock.getPixels()).thenReturn(pixels);

        exportFromPGMToPBM.export(pnmObjectMock);

        assertPixel(pixels[0][0], 0);  // Expect black (0)
        assertPixel(pixels[0][1], 255); // Expect white (255)
        assertPixel(pixels[1][0], 255); // Expect white (255)
        assertPixel(pixels[1][1], 0);   // Expect black (0)
    }

    @Test
    void testExportWithNonP2Header() {
        when(pnmObjectMock.getHeader()).thenReturn("P5");

        exportFromPGMToPBM.export(pnmObjectMock);

        verify(pnmObjectMock, times(0)).getPixels();
    }

    private void assertPixel(Pixel pixel, int expectedValue) {
        assert pixel.getRed() == expectedValue : "Expected red value " + expectedValue + " but found " + pixel.getRed();
        assert pixel.getGreen() == expectedValue : "Expected green value " + expectedValue + " but found " + pixel.getGreen();
        assert pixel.getBlue() == expectedValue : "Expected blue value " + expectedValue + " but found " + pixel.getBlue();
    }
}
