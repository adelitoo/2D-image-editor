package ch.supsi.imageeditor.backend.business.PNMObject;

import ch.supsi.imageeditor.backend.business.exportImages.ExportInterface;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PNMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PPMReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PNMObjectTest {

    private PNMObject pnmObject;
    private ExportInterface exportStrategyMock;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        pnmObject = new PNMObject(tempDir.resolve("test.ppm").toString());
        exportStrategyMock = mock(ExportInterface.class);
        pnmObject.setExportStrategy(exportStrategyMock);
    }

    @Test
    void testPNMObjectCopyConstructor() throws Exception {
        // Locate and copy the resource file to the temp directory
        File resourceFile = new File(getClass().getResource("/P3.ppm").toURI());
        File tempFile = new File(tempDir.toFile(), "P3.ppm");
        Files.copy(resourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Use the copied temp file in the test
        PNMObject original = new PNMObject(tempFile.getAbsolutePath());
        PNMReader reader = new PPMReader();
        original = reader.readImage(original.getFilePath());

        PNMObject copy = new PNMObject(original);

        assertNotEquals(copy, original);
        assertNotEquals(copy.getPixels(), original.getPixels());
    }

    @Test
    void testConstructorAndFilePath() {
        assertEquals(tempDir.resolve("test.ppm").toString(), pnmObject.getFilePath());
    }

    @Test
    void testSetAndGetWidth() {
        pnmObject.setWidth(100);
        assertEquals(100, pnmObject.getWidth());
    }

    @Test
    void testSetAndGetHeight() {
        pnmObject.setHeight(200);
        assertEquals(200, pnmObject.getHeight());
    }

    @Test
    void testSetAndGetMaxVal() {
        pnmObject.setMaxVal(255);
        assertEquals(255, pnmObject.getMaxVal());
    }

    @Test
    void testSetAndGetHeader() {
        pnmObject.setHeader("P3");
        assertEquals("P3", pnmObject.getHeader());
    }

    @Test
    void testSetAndGetPixels() {
        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(255);
        pixels[0][1] = new Pixel(128);
        pixels[1][0] = new Pixel(64);
        pixels[1][1] = new Pixel(0);

        pnmObject.setPixels(pixels);

        assertEquals(pixels, pnmObject.getPixels());
    }

    @Test
    void testExport() {
        pnmObject.export();
        verify(exportStrategyMock, times(1)).export(pnmObject);
    }

    @Test
    void testSetExportStrategy() {
        pnmObject.setExportStrategy(exportStrategyMock);
        assertEquals(exportStrategyMock, pnmObject.getExportStrategy());
    }

    @Test
    void testFilePath() {
        pnmObject.setFilepath(tempDir.resolve("Prova.ppm").toString());
        assertEquals(tempDir.resolve("Prova.ppm").toString(), pnmObject.getFilePath());
    }
}
