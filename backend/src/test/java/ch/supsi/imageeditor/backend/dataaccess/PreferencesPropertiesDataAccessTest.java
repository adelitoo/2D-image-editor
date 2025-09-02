package ch.supsi.imageeditor.backend.dataaccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferencesPropertiesDataAccessTest {

    private PreferencesPropertiesDataAccess preferencesDAO;

    @Mock
    private Path mockPath;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        preferencesDAO = PreferencesPropertiesDataAccess.getInstance();
    }

    @AfterEach
    void resetSingleton() {
        PreferencesPropertiesDataAccess.dao = null;
        PreferencesPropertiesDataAccess.userPreferences = null;
    }

    @Test
    void testSingletonInstance() {
        PreferencesPropertiesDataAccess instance1 = PreferencesPropertiesDataAccess.getInstance();
        PreferencesPropertiesDataAccess instance2 = PreferencesPropertiesDataAccess.getInstance();
        assertSame(instance1, instance2, "The two instances should be the same due to singleton pattern.");
    }

    @Test
    void testUserPreferencesDirectoryExists() throws IOException {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        Path tempDirectory = Files.createTempDirectory("testDirectory");
        doReturn(tempDirectory).when(spyDAO).getUserPreferencesDirectoryPath();

        assertTrue(spyDAO.userPreferencesDirectoryExists());

        Files.deleteIfExists(tempDirectory);
    }

    @Test
    void testCreateUserPreferencesDirectory() throws IOException {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        Path tempDirectory = Files.createTempDirectory("parentDirectory");
        Path newDirectory = tempDirectory.resolve("newDirectory");

        doReturn(newDirectory).when(spyDAO).getUserPreferencesDirectoryPath();

        assertEquals(newDirectory, spyDAO.createUserPreferencesDirectory());

        assertTrue(Files.exists(newDirectory));

        Files.deleteIfExists(newDirectory);
        Files.deleteIfExists(tempDirectory);
    }


    @Test
    void testUserPreferencesFileExists() throws IOException {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        File tempFile = File.createTempFile("preferences", ".properties");
        tempFile.deleteOnExit();

        Path tempFilePath = tempFile.toPath();
        doReturn(tempFilePath).when(spyDAO).getUserPreferencesFilePath();

        assertTrue(spyDAO.userPreferencesFileExists());

    }


    @Test
    void testCreateUserPreferencesFileSuccess() throws IOException {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("key", "value");

        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        File tempFile = File.createTempFile("preferences", ".properties");
        tempFile.deleteOnExit();

        doReturn(tempFile.toPath()).when(spyDAO).getUserPreferencesFilePath();
        doReturn(true).when(spyDAO).userPreferencesDirectoryExists();
        doReturn(false).when(spyDAO).userPreferencesFileExists();

        assertTrue(spyDAO.createUserPreferencesFile(defaultProperties));

        // Verify that the file was created
        assertTrue(tempFile.exists());
    }

    @Test
    void testCreateUserPreferencesFileFailure() {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);
        doReturn(false).when(spyDAO).userPreferencesDirectoryExists();

        Properties defaultProperties = null;
        assertFalse(spyDAO.createUserPreferencesFile(defaultProperties));
    }

    @Test
    void testLoadPreferencesSuccess() throws IOException {
        File tempFile = File.createTempFile("preferences", ".properties");
        tempFile.deleteOnExit();

        Properties properties = new Properties();
        properties.setProperty("key", "value");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            properties.store(fos, null);
        }

        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);
        doReturn(tempFile.toPath()).when(spyDAO).getUserPreferencesFilePath();

        Properties loadedProperties = spyDAO.loadPreferences(tempFile.toPath());
        assertEquals(properties, loadedProperties);
    }

    @Test
    void testLoadPreferencesFailure() {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);
        doReturn(mockPath).when(spyDAO).getUserPreferencesFilePath();

        assertNull(spyDAO.loadPreferences(mockPath));
    }

    @Test
    void testGetPreferencesWithExistingUserPreferences() {
        Properties mockProperties = new Properties();
        mockProperties.setProperty("key", "value");

        PreferencesPropertiesDataAccess.userPreferences = mockProperties;

        Properties result = preferencesDAO.getPreferences();
        assertSame(mockProperties, result);
    }

    @Test
    void testSetPreferenceAndSave() {
        Properties mockProperties = mock(Properties.class);
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        PreferencesPropertiesDataAccess.userPreferences = mockProperties;

        spyDAO.setPreference("key", "value");

        verify(mockProperties, times(1)).setProperty("key", "value");
    }


    @Test
    void testCreateUserPreferencesDirectoryFailure() throws IOException {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        Path mockPath = mock(Path.class);
        doReturn(mockPath).when(spyDAO).getUserPreferencesDirectoryPath();

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(mockPath)).thenThrow(new IOException());

            Path result = spyDAO.createUserPreferencesDirectory();

            assertNull(result, "Expected null when directory creation fails.");
        }
    }

    @Test
    void testCreateUserPreferencesFileWhenFileAndDirectoryExist() {
        Properties mockProperties = new Properties();
        mockProperties.setProperty("key", "value");

        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        doReturn(true).when(spyDAO).userPreferencesDirectoryExists();
        doReturn(true).when(spyDAO).userPreferencesFileExists();

        boolean result = spyDAO.createUserPreferencesFile(mockProperties);
        assertTrue(result, "File creation should return true when file and directory exist.");
    }


    @Test
    void testGetPreferencesFallbackToDefault() {
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        doReturn(false).when(spyDAO).userPreferencesFileExists();
        doReturn(false).when(spyDAO).userPreferencesDirectoryExists();

        Properties defaultPreferences = new Properties();
        defaultPreferences.setProperty("key", "defaultValue");
        doReturn(defaultPreferences).when(spyDAO).loadDefaultPreferences();

        Properties result = spyDAO.getPreferences();
        assertEquals(defaultPreferences, result, "Should load default preferences if no file or directory exists.");
    }


    @Test
    void testSaveProperties() throws IOException {
        Properties mockProperties = new Properties();
        PreferencesPropertiesDataAccess.userPreferences = mockProperties;

        File tempFile = File.createTempFile("preferences", ".properties");
        tempFile.deleteOnExit();

        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);
        doReturn(tempFile.toPath()).when(spyDAO).getUserPreferencesFilePath();

        mockProperties.setProperty("language-tag", "it-CH");
        spyDAO.saveProperties();

        Properties loadedProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(tempFile)) {
            loadedProperties.load(fis);
        }

        assertNotEquals("value", loadedProperties.getProperty("key"));
    }

    @Test
    void testCreateUserPreferencesFileIOException() throws IOException {
        Properties mockDefaultPreferences = mock(Properties.class);
        PreferencesPropertiesDataAccess spyDAO = spy(preferencesDAO);

        doReturn(Paths.get("mock/path/to/preferences.properties")).when(spyDAO).getUserPreferencesFilePath();

        FileOutputStream mockFileOutputStream = mock(FileOutputStream.class);

        doThrow(new IOException("Simulated IO Exception")).when(mockFileOutputStream).write(any(byte[].class));

        boolean result = spyDAO.createUserPreferencesFile(mockDefaultPreferences);

        assertFalse(result, "Expected createUserPreferencesFile to return false when an IOException occurs.");
    }

    @Test
    void testGetPreferencesWithExistingUserPreferencesFile() {
        PreferencesPropertiesDataAccess spyDAO = spy(new PreferencesPropertiesDataAccess());

        doReturn(true).when(spyDAO).userPreferencesFileExists();

        Properties mockProperties = new Properties();
        mockProperties.setProperty("language-tag", "it-CH");
        doReturn(mockProperties).when(spyDAO).loadPreferences(Path.of(Mockito.anyString()));

        Properties result = spyDAO.getPreferences();

        assertNotNull(result, "The result should not be null.");
        assertNotEquals("value1", result.getProperty("key1"), "The property value should be loaded correctly.");
    }

    @Test
    void testLoadDefaultPreferences() {
        PreferencesPropertiesDataAccess spyDAO = spy(new PreferencesPropertiesDataAccess());

        doReturn(true).when(spyDAO).userPreferencesFileExists();

        Properties mockProperties = new Properties();
        mockProperties.setProperty("language-tag", "it-CH");
        doReturn(mockProperties).when(spyDAO).loadPreferences(Path.of(Mockito.anyString()));

        Properties result = spyDAO.getPreferences();
        spyDAO.loadDefaultPreferences();

        assertNotNull(result, "The result should not be null.");
        assertNotEquals("value1", result.getProperty("key1"), "The property value should be loaded correctly.");
    }

    @Test
    void testGetPreferencesLoadsDefaultAndCreatesFile() {
        PreferencesPropertiesDataAccess mockDataAccess = Mockito.spy(new PreferencesPropertiesDataAccess());

        doReturn(false).when(mockDataAccess).userPreferencesFileExists();
        doReturn(false).when(mockDataAccess).userPreferencesDirectoryExists();

        doReturn(Path.of("mockDirectory")).when(mockDataAccess).createUserPreferencesDirectory();

        Properties defaultPreferences = new Properties();
        defaultPreferences.setProperty("language-tag", "it-CH");

        doReturn(defaultPreferences).when(mockDataAccess).loadDefaultPreferences();

        Properties preferences = mockDataAccess.getPreferences();

        assertNotNull(preferences);
        assertEquals("it-CH", preferences.getProperty("language-tag")); // Deve essere caricata la proprietà simulata

        verify(mockDataAccess).loadDefaultPreferences();
        verify(mockDataAccess).createUserPreferencesFile(defaultPreferences);
    }


}
