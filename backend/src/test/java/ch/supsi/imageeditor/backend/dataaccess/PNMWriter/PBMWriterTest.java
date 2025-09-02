package ch.supsi.imageeditor.backend.dataaccess.PNMWriter;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PBMWriterTest {

    private PBMWriter pbmWriter;

    @Mock
    private PNMObject mockPNMObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pbmWriter = new PBMWriter();
    }

    @Test
    void testSaveImageWritesCorrectContent(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("test.pbm").toFile();
        tempFile.deleteOnExit();

        when(mockPNMObject.getFilePath()).thenReturn(tempFile.getAbsolutePath());
        when(mockPNMObject.getWidth()).thenReturn(2);
        when(mockPNMObject.getHeight()).thenReturn(2);
        Pixel[][] pixels = {
                {new Pixel(0, 0, 0), new Pixel(255, 255, 255)},
                {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
        };
        when(mockPNMObject.getPixels()).thenReturn(pixels);

        pbmWriter.saveImage(mockPNMObject);

        String expectedContent = "P1\n2 2\n0 1 \n1 0 \n";
        String actualContent = new String(Files.readAllBytes(tempFile.toPath()));
        assertEquals(expectedContent, actualContent, "The file content is not as expected.");
    }

    @Test
    void testSaveImageHandlesIOException() {
        when(mockPNMObject.getFilePath()).thenReturn("/invalid/path/image.pbm");
        when(mockPNMObject.getWidth()).thenReturn(1);
        when(mockPNMObject.getHeight()).thenReturn(1);
        when(mockPNMObject.getPixels()).thenReturn(new Pixel[][]{{new Pixel(0, 0, 0)}});

        RuntimeException exception = null;

        try {
            pbmWriter.saveImage(mockPNMObject);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertNotNull(exception, "Expected a RuntimeException to be thrown.");
    }

    @Test
    void testWriteFirstTwoLinesThrowsRuntimeException() {
        PNMWriter writer = new PBMWriter();
        BufferedWriter mockedWriter = Mockito.mock(BufferedWriter.class);
        String p = "P1";
        int width = 10;
        int height = 10;

        try {
            doThrow(IOException.class).when(mockedWriter).write(Mockito.anyString());
        } catch (IOException e) {
            throw new RuntimeException("Setup del mock fallito", e);
        }

        assertThrows(RuntimeException.class, () -> writer.writeFirstTwoLines(mockedWriter, p, width, height));
    }
}
