package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PPMReaderTest {

    private Path validFile;
    private Path invalidFile;

    @BeforeEach
    public void setUp() throws IOException {
        validFile = Files.createTempFile("validPPM", ".ppm");
        invalidFile = Files.createTempFile("invalidPPM", ".ppm");

        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("P3\n4 3\n255\n255 0 0   0 255 0   0 0 255   255 255 0\n" +
                    "0 255 255  255 0 255   0 0 0   255 255 255\n" +
                    "255 0 0   0 255 0   0 0 255   255 255 0\n");
        }

        try (FileWriter writer = new FileWriter(invalidFile.toFile())) {
            writer.write("P3\n3 2\n255\n255 0 0 0 255 0 0 0 255 300\n");
        }
    }

    @Test
    public void testReadValidPPMFile() {
        PPMReader reader = new PPMReader();
        PNMObject obj = reader.readImage(validFile.toString());

        assertEquals(4, obj.getWidth());
        assertEquals(3, obj.getHeight());

        assertEquals(255, obj.getPixels()[0][0].getRed());
        assertEquals(0, obj.getPixels()[0][0].getGreen());
        assertEquals(0, obj.getPixels()[0][0].getBlue());

        assertEquals(0, obj.getPixels()[0][1].getRed());
        assertEquals(255, obj.getPixels()[0][1].getGreen());
        assertEquals(0, obj.getPixels()[0][1].getBlue());

        assertEquals(0, obj.getPixels()[0][2].getRed());
        assertEquals(0, obj.getPixels()[0][2].getGreen());
        assertEquals(255, obj.getPixels()[0][2].getBlue());

        assertEquals(255, obj.getPixels()[0][3].getRed());
        assertEquals(255, obj.getPixels()[0][3].getGreen());
        assertEquals(0, obj.getPixels()[0][3].getBlue());

        assertEquals(0, obj.getPixels()[1][0].getRed());
        assertEquals(255, obj.getPixels()[1][0].getGreen());
        assertEquals(255, obj.getPixels()[1][0].getBlue());

        assertEquals(255, obj.getPixels()[1][1].getRed());
        assertEquals(0, obj.getPixels()[1][1].getGreen());
        assertEquals(255, obj.getPixels()[1][1].getBlue());

        assertEquals(0, obj.getPixels()[1][2].getRed());
        assertEquals(0, obj.getPixels()[1][2].getGreen());
        assertEquals(0, obj.getPixels()[1][2].getBlue());

        assertEquals(255, obj.getPixels()[1][3].getRed());
        assertEquals(255, obj.getPixels()[1][3].getGreen());
        assertEquals(255, obj.getPixels()[1][3].getBlue());

        assertEquals(255, obj.getPixels()[2][0].getRed());
        assertEquals(0, obj.getPixels()[2][0].getGreen());
        assertEquals(0, obj.getPixels()[2][0].getBlue());

        assertEquals(0, obj.getPixels()[2][1].getRed());
        assertEquals(255, obj.getPixels()[2][1].getGreen());
        assertEquals(0, obj.getPixels()[2][1].getBlue());

        assertEquals(0, obj.getPixels()[2][2].getRed());
        assertEquals(0, obj.getPixels()[2][2].getGreen());
        assertEquals(255, obj.getPixels()[2][2].getBlue());

        assertEquals(255, obj.getPixels()[2][3].getRed());
        assertEquals(255, obj.getPixels()[2][3].getGreen());
        assertEquals(0, obj.getPixels()[2][3].getBlue());
    }

    @Test
    public void testIncorrectMagicNumber() throws IOException {
        Path fileWithWrongMagicNumber = Files.createTempFile("wrongMagicNumberPPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithWrongMagicNumber.toFile())) {
            writer.write("P2\n3 2\n255\n255 0 0 0 255 0 0 0 255\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithWrongMagicNumber.toString()));
    }

    @Test
    public void fileNotFound() {
        PPMReader reader = new PPMReader();
        assertThrows(RuntimeException.class, () -> reader.readImage("fileNotFound.pgm"));
    }

    @Test
    public void testMissingDimensions() throws IOException {
        Path fileWithoutDimensions = Files.createTempFile("noDimensionsPPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithoutDimensions.toFile())) {
            writer.write("P3\n0 0\n255\n255 0 0\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithoutDimensions.toString()));
    }

    @Test
    public void testInvalidDimensions() throws IOException {
        Path fileWithoutDimensions = Files.createTempFile("noDimensionsPPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithoutDimensions.toFile())) {
            writer.write("P3\n2 3 4\n255\n255 0 0\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithoutDimensions.toString()));
    }

    @Test
    public void testNumberFormatException() throws IOException {
        Path testNumberFormatException = Files.createTempFile("testNumberFormatExceptionPBM", ".ppm");
        try (FileWriter writer = new FileWriter(testNumberFormatException.toFile())) {
            writer.write("P3\nt 2\n0 2 0\n1 0 1\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testNumberFormatException.toString()));
    }

    @Test
    public void testLineWithHashtag() throws IOException {
        Path test = Files.createTempFile("testHashtag", ".ppm");
        try (FileWriter writer = new FileWriter(test.toFile())) {
            writer.write("P3\n2 3\n3\n0 1 0\n#EmptyLne\n\n\n\n\n\n1 0 1\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(test.toString()));
    }

    @Test
    public void testMissingMaxColorValue() throws IOException {
        Path fileWithoutMaxValue = Files.createTempFile("noMaxValuePPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithoutMaxValue.toFile())) {
            writer.write("P3\n3 2\n\n255 0 0 0 255 0 0 0 255\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithoutMaxValue.toString()));
    }

    @Test
    public void testInvalidMaxColorValue() throws IOException {
        Path invalidMaxValueFile = Files.createTempFile("invalidMaxValuePPM", ".ppm");
        try (FileWriter writer = new FileWriter(invalidMaxValueFile.toFile())) {
            writer.write("P3\n3 2\n256\n255 0 0 0 255 0 0 0 255\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(invalidMaxValueFile.toString()));
    }

    @Test
    public void testNonIntegerPixelData() throws IOException {
        Path fileWithNonIntegerData = Files.createTempFile("nonIntegerDataPPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithNonIntegerData.toFile())) {
            writer.write("P3\n3 2\n255\n255 0 x\n255 0 0\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithNonIntegerData.toString()));
    }

    @Test
    public void testNotBetweenIntegerPixelData() throws IOException {
        Path fileWithNonIntegerData = Files.createTempFile("nonIntegerDataPPM", ".ppm");
        try (FileWriter writer = new FileWriter(fileWithNonIntegerData.toFile())) {
            writer.write("P3\n4 3\n255\n255 0 0   0 255 0   0 0 255   255 255 0\n" +
                    "0 255 255  256 0 255   0 0 0   255 255 255\n" +
                    "255 0 0   0 255 0   0 0 255   255 255 0\n");
        }
        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithNonIntegerData.toString()));
    }

    @Test
    public void testExtraPixelData() {
        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(invalidFile.toString()));
    }

    @Test
    public void testEmptyLines() throws IOException {
        Path testEmptyLines = Files.createTempFile("testEmptyLines", ".ppm");
        try (FileWriter writer = new FileWriter(testEmptyLines.toFile())) {
            writer.write("#Prova\n#Prova\n#Prova\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testEmptyLines.toString()));
    }

    @Test
    public void testEmptyFile() throws IOException {
        Path emptyFile = Files.createTempFile("emptyPPM", ".ppm");
        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(emptyFile.toString()));
    }

    @Test
    public void testZeroDimensions() throws IOException {
        Path zeroDimensionsFile = Files.createTempFile("zeroDimensionsPPM", ".ppm");
        try (FileWriter writer = new FileWriter(zeroDimensionsFile.toFile())) {
            writer.write("P3\n0 0\n255\n255 0 0\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(zeroDimensionsFile.toString()));
    }

    @Test
    public void testOutOfRangePixelValue() throws IOException {
        Path outOfRangePixelFile = Files.createTempFile("outOfRangePixelPPM", ".ppm");
        try (FileWriter writer = new FileWriter(outOfRangePixelFile.toFile())) {
            writer.write("P3\n3 2\n255\n255 0 0 0 255 0 0 0 300\n");
        }

        PPMReader reader = new PPMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(outOfRangePixelFile.toString()));
    }
}
