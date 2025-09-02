package ch.supsi.imageeditor.backend.business.transformationsOptions;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class FlipImageSideToSideTest {

    private FlipImageSideToSide flipImageSideToSide;
    private PNMObject pnmObject;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        flipImageSideToSide = new FlipImageSideToSide();
    }

    @Test
    public void constructor() {
        FlipImageSideToSide obj = new FlipImageSideToSide();
        assertNotNull(obj);
    }

    @Test
    void testDoTransformation() throws Exception {
        // Create temporary test file by copying a resource file
        File resourceFile = new File(getClass().getResource("/test.ppm").toURI());
        File tempFile = new File(tempDir.toFile(), "test.ppm");
        Files.copy(resourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(255, 0, 0); // Rosso
        pixels[0][1] = new Pixel(0, 255, 0); // Verde
        pixels[1][0] = new Pixel(0, 0, 255); // Blu
        pixels[1][1] = new Pixel(255, 255, 0); // Giallo

        pnmObject = new PNMObject(tempFile.getAbsolutePath());
        pnmObject.setPixels(pixels);
        pnmObject.setWidth(2);
        pnmObject.setHeight(2);

        flipImageSideToSide.doTransformation(pnmObject);

        assertEquals(new Pixel(0, 255, 0).toString(), pnmObject.getPixels()[0][0].toString());
        assertEquals(new Pixel(255, 0, 0).toString(), pnmObject.getPixels()[0][1].toString());
        assertEquals(new Pixel(255, 255, 0).toString(), pnmObject.getPixels()[1][0].toString());
        assertEquals(new Pixel(0, 0, 255).toString(), pnmObject.getPixels()[1][1].toString());
    }

    @Test
    void testDoTransformationEmptyPixels() throws Exception {
        // Create temporary test file
        File tempFile = new File(tempDir.toFile(), "test.ppm");

        pnmObject = new PNMObject(tempFile.getAbsolutePath());
        pnmObject.setPixels(null);

        flipImageSideToSide.doTransformation(pnmObject);

        assertNull(pnmObject.getPixels());
    }

    @Test
    void testDoTransformationEmptyImage() throws Exception {
        // Create temporary test file
        File tempFile = new File(tempDir.toFile(), "test.ppm");

        pnmObject = new PNMObject(tempFile.getAbsolutePath());
        pnmObject.setPixels(new Pixel[0][0]);

        flipImageSideToSide.doTransformation(pnmObject);

        assertEquals(0, pnmObject.getHeight());
        assertEquals(0, pnmObject.getWidth());
    }
}
