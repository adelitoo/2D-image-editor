package ch.supsi.imageeditor.backend.business.exportImages;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ExportFromPPMToPGMTest {

    @Mock
    private PNMObject pnmObjectMock;

    private ExportFromPPMToPGM exportFromPPMToPGM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        exportFromPPMToPGM = new ExportFromPPMToPGM();
    }
    @Test
    public void constructor() {
        ExportFromPPMToPGM exportFromPGMToPBM1 = new ExportFromPPMToPGM();
        Assertions.assertNotNull(exportFromPGMToPBM1);
    }

    @Test
    void testExportWithP3Header() {
        when(pnmObjectMock.getHeader()).thenReturn("P3");
        when(pnmObjectMock.getWidth()).thenReturn(2);
        when(pnmObjectMock.getHeight()).thenReturn(2);

        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(100, 150, 200);
        pixels[0][1] = new Pixel(50, 100, 150);
        pixels[1][0] = new Pixel(255, 255, 255);
        pixels[1][1] = new Pixel(0, 0, 0);

        when(pnmObjectMock.getPixels()).thenReturn(pixels);

        exportFromPPMToPGM.export(pnmObjectMock);

        assertPixel(pixels[0][0], 140);
        assertPixel(pixels[0][1], 90);
        assertPixel(pixels[1][0], 255);
        assertPixel(pixels[1][1], 0);

        verify(pnmObjectMock).setHeader("P2");
    }

    @Test
    void testExportWithNonP3Header() {
        when(pnmObjectMock.getHeader()).thenReturn("P6");

        exportFromPPMToPGM.export(pnmObjectMock);

        verify(pnmObjectMock, times(0)).getPixels();
    }

    private void assertPixel(Pixel pixel, int expectedValue) {
        assert pixel.getRed() == expectedValue : "Expected red value " + expectedValue + " but found " + pixel.getRed();
        assert pixel.getGreen() == expectedValue : "Expected green value " + expectedValue + " but found " + pixel.getGreen();
        assert pixel.getBlue() == expectedValue : "Expected blue value " + expectedValue + " but found " + pixel.getBlue();
    }
}
