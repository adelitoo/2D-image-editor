package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadImagePNMModelTest {

    private LoadImagePNMModel loadImagePNMModel;

    @BeforeEach
    void setUp() {
        Field instanceField;
        try {
            instanceField = LoadImagePNMModel.class.getDeclaredField("myself");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        loadImagePNMModel = LoadImagePNMModel.getInstance();
    }
    @Test
    public void constructor() {
        LoadImagePNMModel loadImagePNMModel1 = new LoadImagePNMModel();
        Assertions.assertNotNull(loadImagePNMModel1);
    }

    @Test
    public void testInstanceNull() {
        LoadImagePNMModel loadImagePNMModel1 = LoadImagePNMModel.getInstance();
        Assertions.assertNotNull(loadImagePNMModel1);
    }

    @Test
    void testSingletonInstance() {
        LoadImagePNMModel instance1 = LoadImagePNMModel.getInstance();
        LoadImagePNMModel instance2 = LoadImagePNMModel.getInstance();
        assertSame(instance1, instance2, "Instances should be the same (singleton).");
    }

    @Test
    void testReadPBMImage() throws Exception {
        // Use a temporary directory for the test file
        Path tempDir = Files.createTempDirectory("test-dir");
        Path tempFile = tempDir.resolve("test.pbm");

        String pbmContent = "P1\n2 2\n0 1\n1 0";

        Files.writeString(tempFile, pbmContent);

        LoadImagePNMModel loadImagePNMModel = LoadImagePNMModel.getInstance();

        Pixel[][] pixels = loadImagePNMModel.readImage(tempFile.toString());

        assertNotNull(pixels, "L'array di pixel non deve essere nullo.");
        assertEquals(2, pixels.length, "La larghezza dell'immagine deve essere 2.");
        assertEquals(2, pixels[0].length, "L'altezza dell'immagine deve essere 2.");

        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testReadPGMImage() throws Exception {
        Path tempDir = Files.createTempDirectory("test-dir");
        Path tempFile = tempDir.resolve("test.pgm");

        String pgmContent = "P2\n2 2\n255\n0 255\n128 64"; // Contenuto PGM valido

        Files.writeString(tempFile, pgmContent);

        LoadImagePNMModel loadImagePNMModel = LoadImagePNMModel.getInstance();

        Pixel[][] pixels = loadImagePNMModel.readImage(tempFile.toString());

        assertNotNull(pixels, "L'array di pixel non deve essere nullo.");
        assertEquals(2, pixels.length, "La larghezza dell'immagine deve essere 2.");
        assertEquals(2, pixels[0].length, "L'altezza dell'immagine deve essere 2.");

        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a)) // Delete files first
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testReadPPMImage() throws Exception {
        Path tempDir = Files.createTempDirectory("test-dir");
        Path tempFile = tempDir.resolve("test.ppm");

        String ppmContent = "P3\n2 2\n255\n255 0 0 0 255 0\n0 0 255 255 255 255"; // Contenuto PPM valido

        Files.writeString(tempFile, ppmContent);

        LoadImagePNMModel loadImagePNMModel = LoadImagePNMModel.getInstance();

        Pixel[][] pixels = loadImagePNMModel.readImage(tempFile.toString());

        assertNotNull(pixels, "L'array di pixel non deve essere nullo.");
        assertEquals(2, pixels.length, "La larghezza dell'immagine deve essere 2.");
        assertEquals(2, pixels[0].length, "L'altezza dell'immagine deve essere 2.");

        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a)) // Delete files first
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testUnsupportedFileType() {
        String filePath = "unsupported.txt";

        LoadImagePNMModel loadImagePNMModel = LoadImagePNMModel.getInstance();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loadImagePNMModel.readImage(filePath);
        });
        assertEquals("Unsupported file type.", exception.getMessage(), "Il messaggio dell'eccezione deve essere corretto.");
    }
    @Test
    void testSetAndGetLoadObj() {
        PNMObject mockPNMObject = mock(PNMObject.class);

        loadImagePNMModel.setLoadObj(mockPNMObject);

        PNMObject retrievedObject = loadImagePNMModel.getLoadObj();

        assertSame(mockPNMObject, retrievedObject, "L'oggetto recuperato deve essere lo stesso impostato.");
    }

}
