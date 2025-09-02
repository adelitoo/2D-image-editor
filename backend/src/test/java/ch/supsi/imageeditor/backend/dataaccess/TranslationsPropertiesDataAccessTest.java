package ch.supsi.imageeditor.backend.dataaccess;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TranslationsPropertiesDataAccessTest {

    TranslationsPropertiesDataAccess translationsDAO;

    @BeforeEach
    void setup() {
        translationsDAO = TranslationsPropertiesDataAccess.getInstance();
    }

    @AfterEach
    void resetSingleton() {
        TranslationsPropertiesDataAccess.myself = null;
    }

    @Test
    void testSingletonInstance() {
        TranslationsPropertiesDataAccess instance1 = TranslationsPropertiesDataAccess.getInstance();
        TranslationsPropertiesDataAccess instance2 = TranslationsPropertiesDataAccess.getInstance();

        assertSame(instance1, instance2, "The singleton instance should always return the same object.");
    }

    @Test
    void testGetBundleWithValidLocale() {
        ResourceBundle bundle = translationsDAO.getBundle(Locale.forLanguageTag("it-CH"));
        assertNotNull(bundle, "The ResourceBundle should not be null for a valid locale.");
    }




}
