package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PBMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PGMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PNMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PPMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PBMWriter;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PGMWriter;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PPMWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveImagePNMModelTest {

    private SaveImagePNMModel saveImagePNMModel;

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = SaveImagePNMModel.class.getDeclaredField("myself");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        saveImagePNMModel = SaveImagePNMModel.getInstance();
    }

    @Test
    void testSingletonInstance() {
        SaveImagePNMModel firstInstance = SaveImagePNMModel.getInstance();
        SaveImagePNMModel secondInstance = SaveImagePNMModel.getInstance();
        assertSame(firstInstance, secondInstance, "Le due istanze devono essere uguali (singleton).");
    }

    @Test
    void testSaveImageWithPBMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P1.pbm").getPath());
        PNMReader reader = new PBMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());


        saveImagePNMModel.saveImage(mockPNMObject);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);
        Object pnmWriterInstance = writerField.get(saveImagePNMModel);
        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PBMWriter, "pnmWriter dovrebbe essere un'istanza di PBMWriter");
    }
    @Test
    void testSaveImageWithPGMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P2.pgm").getPath());
        PNMReader reader = new PGMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());


        saveImagePNMModel.saveImage(mockPNMObject);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);
        Object pnmWriterInstance = writerField.get(saveImagePNMModel);
        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PGMWriter, "pnmWriter dovrebbe essere un'istanza di PBMWriter");
    }

    @Test
    void testSaveImageWithPPMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P3.ppm").getPath());
        PNMReader reader = new PPMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());


        saveImagePNMModel.saveImage(mockPNMObject);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);
        Object pnmWriterInstance = writerField.get(saveImagePNMModel);
        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PPMWriter, "pnmWriter dovrebbe essere un'istanza di PBMWriter");
    }


    @Test
    void testSaveAsImageWithPBMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P1.pbm").getPath());

        PNMReader reader = new PBMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());

        String absolutePath = getClass().getResource("/Output.pbm").getPath();

        saveImagePNMModel.saveAsImage(mockPNMObject, absolutePath);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);

        Object pnmWriterInstance = writerField.get(saveImagePNMModel);

        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PBMWriter, "pnmWriter dovrebbe essere un'istanza di PBMWriter");
    }

    @Test
    void testSaveAsImageWithPGMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P2.pgm").getPath());

        PNMReader reader = new PGMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());

        String absolutePath = getClass().getResource("/Output.pgm").getPath();

        saveImagePNMModel.saveAsImage(mockPNMObject, absolutePath);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);

        Object pnmWriterInstance = writerField.get(saveImagePNMModel);

        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PGMWriter, "pnmWriter dovrebbe essere un'istanza di PGMWriter");
    }

    @Test
    void testSaveAsImageWithPPMFilePath() throws NoSuchFieldException, IllegalAccessException {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P3.ppm").getPath());

        PNMReader reader = new PPMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());

        String absolutePath = getClass().getResource("/Output.ppm").getPath();

        saveImagePNMModel.saveAsImage(mockPNMObject, absolutePath);

        Field writerField = SaveImagePNMModel.class.getDeclaredField("pnmWriter");
        writerField.setAccessible(true);

        Object pnmWriterInstance = writerField.get(saveImagePNMModel);

        assertNotNull(pnmWriterInstance, "pnmWriter dovrebbe essere inizializzato");

        assertTrue(pnmWriterInstance instanceof PPMWriter, "pnmWriter dovrebbe essere un'istanza di PPMWriter");
    }


    @Test
    void testSaveImageWithUnsupportedFormat() {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn("image.txt");

        assertThrows(AssertionError.class, () -> saveImagePNMModel.saveImage(mockPNMObject));
    }

    @Test
    void testSaveAsImageCallsSaveImage() {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P2.pgm").getPath());
        String absolutePath = getClass().getResource("/Output.pbm").getPath();
        PNMReader reader = new PGMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());
        SaveImagePNMModel spySaveImagePNMModel = spy(saveImagePNMModel);

        spySaveImagePNMModel.saveAsImage(mockPNMObject, absolutePath);

        verify(spySaveImagePNMModel).saveImage(any(PNMObject.class));
    }

    @Test
    void testSaveAsImageCallsSaveImage2() {
        PNMObject mockPNMObject = mock(PNMObject.class);

        when(mockPNMObject.getFilePath()).thenReturn(getClass().getResource("/P3.ppm").getPath());
        String absolutePath = getClass().getResource("/Output.pbm").getPath();
        PNMReader reader = new PPMReader();
        mockPNMObject = reader.readImage(mockPNMObject.getFilePath());
        SaveImagePNMModel spySaveImagePNMModel = spy(saveImagePNMModel);

        spySaveImagePNMModel.saveAsImage(mockPNMObject, absolutePath);

        verify(spySaveImagePNMModel).saveImage(any(PNMObject.class));
    }



}
