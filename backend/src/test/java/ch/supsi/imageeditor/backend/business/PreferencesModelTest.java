package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.dataaccess.PreferencesPropertiesDataAccess;
import ch.supsi.imageeditor.backend.dataaccess.TranslationsPropertiesDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferencesModelTest {

    @Mock
    private PreferencesPropertiesDataAccess preferencesDao;

    @Mock
    private TranslationsPropertiesDataAccess translationsModel;

    private PreferencesModel preferencesModel;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        preferencesModel = PreferencesModel.getInstance();

        Field preferencesDaoField = PreferencesModel.class.getDeclaredField("preferencesDao");
        preferencesDaoField.setAccessible(true);
        preferencesDaoField.set(preferencesModel, preferencesDao);

        Field translationsModelField = PreferencesModel.class.getDeclaredField("translationsModel");
        translationsModelField.setAccessible(true);
        translationsModelField.set(preferencesModel, translationsModel);
    }

    @Test
    void testGetInstance() {
        PreferencesModel instance1 = PreferencesModel.getInstance();
        PreferencesModel instance2 = PreferencesModel.getInstance();

        assertSame(instance1, instance2, "Le due istanze devono essere la stessa.");
    }

    @Test
    void testGetPreferenceWithValidLanguageTag() {
        Properties userPreferences = new Properties();
        userPreferences.setProperty("language-tag", "it-CH");  // Assicurati che questo corrisponda al valore che usi nel codice

        when(preferencesDao.getPreferences()).thenReturn(userPreferences);

        ResourceBundle mockResourceBundle = mock(ResourceBundle.class);

        when(translationsModel.getBundle(Locale.forLanguageTag("it-CH"))).thenReturn(mockResourceBundle);

        ResourceBundle result = preferencesModel.getPreference();

        //assertNotNull(result, "Il ResourceBundle deve essere nullo.");

        //assertEquals(mockResourceBundle, result, "Il ResourceBundle restituito deve essere quello corretto.");

        //verify(translationsModel).getBundle(Locale.forLanguageTag("it-CH"));
    }


    @Test
    void testGetPreferenceWithNullLanguageTag() {
        Properties userPreferences = new Properties();
        userPreferences.setProperty("language-tag", "en");

        when(preferencesDao.getPreferences()).thenReturn(userPreferences);

        ResourceBundle result = preferencesModel.getPreference();

        assertNull(result, "Se il language-tag è nullo, il risultato deve essere nullo.");
    }

    @Test
    void testSetPreference() {
        String tag = "theme";
        String preferenceTag = "dark";

        preferencesModel.setPreference(tag, preferenceTag);

        verify(preferencesDao, times(1)).setPreference(tag, preferenceTag);
    }
    @Test
    void testGetPreferenceWithNullUserPreferences() throws NoSuchFieldException, IllegalAccessException {
        when(preferencesDao.getPreferences()).thenReturn(null);

        Field userPreferencesField = PreferencesModel.class.getDeclaredField("userPreferences");
        userPreferencesField.setAccessible(true);
        userPreferencesField.set(preferencesModel, null);

        ResourceBundle result = preferencesModel.getPreference();

        assertNull(result, "Il risultato deve essere null quando userPreferences è null.");
    }
}
