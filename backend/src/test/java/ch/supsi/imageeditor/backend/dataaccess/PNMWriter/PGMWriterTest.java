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

class PGMWriterTest {

    private PGMWriter pgmWriter;

    @Mock
    private PNMObject mockPNMObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pgmWriter = new PGMWriter();
    }

    @Test
    void testSaveImageWritesCorrectContent(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("test.pgm").toFile();
        tempFile.deleteOnExit();

        // Mocking PNMObject behavior
        when(mockPNMObject.getFilePath()).thenReturn(tempFile.getAbsolutePath());
        when(mockPNMObject.getWidth()).thenReturn(2);
        when(mockPNMObject.getHeight()).thenReturn(2);
        Pixel[][] pixels = {
                {new Pixel(100, 0, 0), new Pixel(255, 0, 0)},
                {new Pixel(50, 0, 0), new Pixel(200, 0, 0)}
        };
        when(mockPNMObject.getPixels()).thenReturn(pixels);

        pgmWriter.saveImage(mockPNMObject);

        // Verify file content
        String expectedContent = """
            P2
            2 2
            255
            100 255\s
            50 200\s
            """;
        String actualContent = new String(Files.readAllBytes(tempFile.toPath()));
        assertEquals(expectedContent, actualContent, "The file content is not as expected.");
    }

    @Test
    void testSaveImageHandlesIOException() {
        when(mockPNMObject.getFilePath()).thenReturn("/invalid/path/image.pgm");
        when(mockPNMObject.getWidth()).thenReturn(1);
        when(mockPNMObject.getHeight()).thenReturn(1);
        when(mockPNMObject.getPixels()).thenReturn(new Pixel[][]{{new Pixel(0, 0, 0)}});

        RuntimeException exception = null;

        try {
            pgmWriter.saveImage(mockPNMObject);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertNotNull(exception, "Expected a RuntimeException to be thrown.");
    }

    @Test
    void testGetMaxValCalculatesCorrectly() {
        Pixel[][] pixels = {
                {new Pixel(100, 0, 0), new Pixel(255, 0, 0)},
                {new Pixel(50, 0, 0), new Pixel(200, 0, 0)}
        };
        when(mockPNMObject.getPixels()).thenReturn(pixels);
        when(mockPNMObject.getWidth()).thenReturn(2);
        when(mockPNMObject.getHeight()).thenReturn(2);

        int maxVal = pgmWriter.getMaxVal(mockPNMObject);

        assertEquals(255, maxVal, "The max value should be 255.");
    }
}
