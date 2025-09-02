package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class PGMReaderTest {

    private Path validFile;
    private Path invalidFile;

    @BeforeEach
    public void setUp() throws IOException {
        validFile = Files.createTempFile("validPGM", ".pgm");
        invalidFile = Files.createTempFile("invalidPGM", ".pgm");

        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("P2\n3 2\n255\n0 128 255\n255 0 128\n");
        }

        try (FileWriter writer = new FileWriter(invalidFile.toFile())) {
            writer.write("P2\n3 2\n255\n0 128 255 255\n");
        }
    }

    @Test
    public void testReadValidPGMFile() {
        PGMReader reader = new PGMReader();
        PNMObject obj = reader.readImage(validFile.toString());

        assertEquals(3, obj.getWidth());
        assertEquals(2, obj.getHeight());
        assertEquals(0, obj.getPixels()[0][0].getRed());
        assertEquals(128, obj.getPixels()[0][1].getRed());
        assertEquals(255, obj.getPixels()[0][2].getRed());
        assertEquals(255, obj.getPixels()[1][0].getRed());
        assertEquals(0, obj.getPixels()[1][1].getRed());
        assertEquals(128, obj.getPixels()[1][2].getRed());
    }

    @Test
    public void testIncorrectMagicNumber() throws IOException {
        Path fileWithWrongMagicNumber = Files.createTempFile("wrongMagicNumberPGM", ".pgm");
        try (FileWriter writer = new FileWriter(fileWithWrongMagicNumber.toFile())) {
            writer.write("P1\n3 2\n255\n0 128 255\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithWrongMagicNumber.toString()));
    }

    @Test
    public void testNumberFormatException() throws IOException {
        Path testNumberFormatException = Files.createTempFile("testNumberFormatExceptionPBM", ".pgm");
        try (FileWriter writer = new FileWriter(testNumberFormatException.toFile())) {
            writer.write("P2\nt 2\n0 2 0\n1 0 1\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testNumberFormatException.toString()));
    }

    @Test
    public void testMissingDimensions() throws IOException {
        Path fileWithoutDimensions = Files.createTempFile("noDimensionsPGM", ".pgm");
        try (FileWriter writer = new FileWriter(fileWithoutDimensions.toFile())) {
            writer.write("P2\n0 1 0\n255\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithoutDimensions.toString()));
    }

    @Test
    public void testMissingMaxGrayscaleValue() throws IOException {
        Path fileWithoutMaxValue = Files.createTempFile("noMaxValuePGM", ".pgm");
        try (FileWriter writer = new FileWriter(fileWithoutMaxValue.toFile())) {
            writer.write("P2\n3 2\n\n0 128 255\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithoutMaxValue.toString()));
    }

    @Test
    public void testInvalidMaxGrayscaleValue() throws IOException {
        Path invalidMaxValueFile = Files.createTempFile("invalidMaxValuePGM", ".pgm");
        try (FileWriter writer = new FileWriter(invalidMaxValueFile.toFile())) {
            writer.write("P2\n3 2\n256\n0 128 255\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(invalidMaxValueFile.toString()));
    }

    @Test
    public void testInvalidMaxGrayscaleValueFormat() throws IOException {
        Path testInvalidMaxGrayscaleValueFormat = Files.createTempFile("testInvalidMaxGrayscaleValueFormat", ".pgm");
        try (FileWriter writer = new FileWriter(testInvalidMaxGrayscaleValueFormat.toFile())) {
            writer.write("P2\n3 2\nt\n0 128 255\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testInvalidMaxGrayscaleValueFormat.toString()));
    }

    @Test
    public void testInvalidPixelValueFormat() throws IOException {
        Path testInvalidMaxGrayscaleValueFormat = Files.createTempFile("testInvalidMaxGrayscaleValueFormat", ".pgm");
        try (FileWriter writer = new FileWriter(testInvalidMaxGrayscaleValueFormat.toFile())) {
            writer.write("P2\n3 2\n255\n0 128 t\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testInvalidMaxGrayscaleValueFormat.toString()));
    }

    @Test
    public void testLineWithHashtag() throws IOException {
        Path test = Files.createTempFile("testHashtag", ".pgm");
        try (FileWriter writer = new FileWriter(test.toFile())) {
            writer.write("P2\n3 2\n1\n0 1 0\n#EmptyLne\n\n\n\n\n\n1 0 1\n");
        }

        PGMReader reader = new PGMReader();
        reader.readImage(test.toString());
    }

    @Test
    public void testNonIntegerPixelData() throws IOException {
        Path fileWithNonIntegerData = Files.createTempFile("nonIntegerDataPGM", ".pgm");
        try (FileWriter writer = new FileWriter(fileWithNonIntegerData.toFile())) {
            writer.write("P2\n3 2\n255\n0 128 a\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(fileWithNonIntegerData.toString()));
    }

    @Test
    public void testExtraPixelData() {
        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(invalidFile.toString()));
    }

    @Test
    public void testEmptyFile() throws IOException {
        Path emptyFile = Files.createTempFile("emptyPGM", ".pgm");
        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(emptyFile.toString()));
    }

    @Test
    public void testZeroDimensions() throws IOException {
        Path zeroDimensionsFile = Files.createTempFile("zeroDimensionsPGM", ".pgm");
        try (FileWriter writer = new FileWriter(zeroDimensionsFile.toFile())) {
            writer.write("P2\n0 0\n255\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(zeroDimensionsFile.toString()));
    }

    @Test
    public void testEmptyLines() throws IOException {
        Path testEmptyLines = Files.createTempFile("testEmptyLines", ".pgm");
        try (FileWriter writer = new FileWriter(testEmptyLines.toFile())) {
            writer.write("#Prova\n#Prova\n#Prova\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(testEmptyLines.toString()));
    }

    @Test
    public void testOutOfRangePixelValue() throws IOException {
        Path outOfRangePixelFile = Files.createTempFile("outOfRangePixelPGM", ".pgm");
        try (FileWriter writer = new FileWriter(outOfRangePixelFile.toFile())) {
            writer.write("P2\n3 2\n255\n0 128 300\n255 0 128\n");
        }

        PGMReader reader = new PGMReader();
        assertThrows(IllegalArgumentException.class, () -> reader.readImage(outOfRangePixelFile.toString()));
    }
}
