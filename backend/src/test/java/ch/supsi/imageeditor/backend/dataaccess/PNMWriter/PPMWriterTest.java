package ch.supsi.imageeditor.backend.dataaccess.PNMWriter;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PPMWriterTest {

    private PPMWriter ppmWriter;

    @Mock
    private PNMObject mockPNMObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ppmWriter = new PPMWriter();
    }

    @Test
    void testSaveImageWritesCorrectContent(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("test.ppm").toFile();
        tempFile.deleteOnExit();

        // Mocking PNMObject behavior
        when(mockPNMObject.getFilePath()).thenReturn(tempFile.getAbsolutePath());
        when(mockPNMObject.getWidth()).thenReturn(2);
        when(mockPNMObject.getHeight()).thenReturn(2);
        Pixel[][] pixels = {
                {new Pixel(100, 50, 30), new Pixel(255, 0, 0)},
                {new Pixel(50, 100, 150), new Pixel(200, 255, 50)}
        };
        when(mockPNMObject.getPixels()).thenReturn(pixels);

        ppmWriter.saveImage(mockPNMObject);

        // Verify file content
        String expectedContent = """
            P3
            2 2
            255
            100 50 30 255 0 0\s
            50 100 150 200 255 50\s
            """;
        String actualContent = new String(Files.readAllBytes(tempFile.toPath()));
        assertEquals(expectedContent, actualContent, "The file content is not as expected.");
    }

    @Test
    void testSaveImageHandlesIOException() {
        when(mockPNMObject.getFilePath()).thenReturn("/invalid/path/image.ppm");
        when(mockPNMObject.getWidth()).thenReturn(1);
        when(mockPNMObject.getHeight()).thenReturn(1);
        when(mockPNMObject.getPixels()).thenReturn(new Pixel[][]{{new Pixel(0, 0, 0)}});

        RuntimeException exception = null;

        try {
            ppmWriter.saveImage(mockPNMObject);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertNotNull(exception, "Expected a RuntimeException to be thrown.");
    }

    @Test
    void testGetMaxValCalculatesCorrectly() {
        Pixel[][] pixels = {
                {new Pixel(100, 50, 30), new Pixel(255, 0, 0)},
                {new Pixel(50, 100, 150), new Pixel(200, 255, 50)}
        };
        when(mockPNMObject.getPixels()).thenReturn(pixels);
        when(mockPNMObject.getWidth()).thenReturn(2);
        when(mockPNMObject.getHeight()).thenReturn(2);

        int maxVal = ppmWriter.getMaxVal(mockPNMObject);

        assertEquals(255, maxVal, "The max value should be 255.");
    }
}
