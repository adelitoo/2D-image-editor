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

public class PBMReaderTest {

    private Path validFile;
    private Path invalidFile;

    @BeforeEach
    public void setUp() throws IOException {
        validFile = Files.createTempFile("validPBM", ".pbm");
        invalidFile = Files.createTempFile("invalidPBM", ".pbm");

        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("P1\n3 2\n0 1 0\n1 0 1\n");
        }

        try (FileWriter writer = new FileWriter(invalidFile.toFile())) {
            writer.write("P1\n3 2\n0 1 0 1 1\n");
        }
    }

    @Test
    public void testReadValidPBMFile() {
        PBMReader reader = new PBMReader();
        PNMObject obj = reader.readImage(validFile.toString());

        assertEquals(3, obj.getWidth());
        assertEquals(2, obj.getHeight());

        assertEquals(255, obj.getPixels()[0][0].getRed());
        assertEquals(0, obj.getPixels()[0][1].getRed());
        assertEquals(255, obj.getPixels()[0][2].getRed());
        assertEquals(0, obj.getPixels()[1][0].getRed());
        assertEquals(255, obj.getPixels()[1][1].getRed());
        assertEquals(0, obj.getPixels()[1][2].getRed());
    }

    @Test
    public void testIncorrectMagicNumber() throws IOException {
        Path fileWithWrongMagicNumber = Files.createTempFile("wrongMagicNumberPBM", ".pbm");
        try (FileWriter writer = new FileWriter(fileWithWrongMagicNumber.toFile())) {
            writer.write("P2\n3 2\n0 1 0\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(fileWithWrongMagicNumber.toString());
        });
    }

    @Test
    public void testLineWithHashtag() throws IOException {
        Path test = Files.createTempFile("testHashtag", ".pbm");
        try (FileWriter writer = new FileWriter(test.toFile())) {
            writer.write("P1\n3 2\n#EmptyLine\n0 1 0\n#EmptyLne\n\n\n\n\n\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        reader.readImage(test.toString());
    }

    @Test
    public void testMissingDimensions() throws IOException {
        Path fileWithoutDimensions = Files.createTempFile("noDimensionsPBM", ".pbm");
        try (FileWriter writer = new FileWriter(fileWithoutDimensions.toFile())) {
            writer.write("P1\n0 1 0\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(fileWithoutDimensions.toString());
        });
    }

    @Test
    public void testBadPixels() throws IOException {
        Path badPixels = Files.createTempFile("badPixels", ".pbm");
        try (FileWriter writer = new FileWriter(badPixels.toFile())) {
            writer.write("P1\n3 2\n0 65 0\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(badPixels.toString());
        });
    }

    @Test
    public void testEmptyLines() throws IOException {
        Path testEmptyLines = Files.createTempFile("testEmptyLines", ".pbm");
        try (FileWriter writer = new FileWriter(testEmptyLines.toFile())) {
            writer.write("#Prova\n#Prova\n#Prova\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(testEmptyLines.toString());
        });
    }

    @Test
    public void testNonBinaryPixelData() throws IOException {
        Path fileWithNonBinaryData = Files.createTempFile("nonBinaryDataPBM", ".pbm");
        try (FileWriter writer = new FileWriter(fileWithNonBinaryData.toFile())) {
            writer.write("P1\n3 2\n0 2 0\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(fileWithNonBinaryData.toString());
        });
    }

    @Test
    public void testNumberFormatException() throws IOException {
        Path testNumberFormatException = Files.createTempFile("testNumberFormatExceptionPBM", ".pbm");
        try (FileWriter writer = new FileWriter(testNumberFormatException.toFile())) {
            writer.write("P1\nt 2\n0 2 0\n1 0 1\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(testNumberFormatException.toString());
        });
    }

    @Test
    public void testExtraPixelData() {
        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(invalidFile.toString());
        });
    }

    @Test
    public void testEmptyFile() throws IOException {
        Path emptyFile = Files.createTempFile("emptyPBM", ".pbm");
        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(emptyFile.toString());
        });
    }

    @Test
    public void testZeroDimensions() throws IOException {
        Path zeroDimensionsFile = Files.createTempFile("zeroDimensionsPBM", ".pbm");
        try (FileWriter writer = new FileWriter(zeroDimensionsFile.toFile())) {
            writer.write("P1\n0 0\n");
        }

        PBMReader reader = new PBMReader();
        assertThrows(IllegalArgumentException.class, () -> {
            reader.readImage(zeroDimensionsFile.toString());
        });
    }
}
