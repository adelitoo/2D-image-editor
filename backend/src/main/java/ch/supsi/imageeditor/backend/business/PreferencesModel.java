package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.dataaccess.PreferencesDataAccessInterface;
import ch.supsi.imageeditor.backend.dataaccess.PreferencesPropertiesDataAccess;
import ch.supsi.imageeditor.backend.dataaccess.TranslationsDataAccessInterface;
import ch.supsi.imageeditor.backend.dataaccess.TranslationsPropertiesDataAccess;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PreferencesModel implements PreferencesBusinessInterface {

    private static PreferencesModel myself;

    private final PreferencesDataAccessInterface preferencesDao;
    private final Properties userPreferences;
    private final TranslationsDataAccessInterface translationsModel;

    protected PreferencesModel() {
        this.translationsModel = TranslationsPropertiesDataAccess.getInstance();
        this.preferencesDao = PreferencesPropertiesDataAccess.getInstance();
        this.userPreferences = preferencesDao.getPreferences();
    }

    public static PreferencesModel getInstance() {
        if (myself == null) {
            myself = new PreferencesModel();
        }

        return myself;
    }

    @Override
    public ResourceBundle getPreference() {
        if (userPreferences == null) {
            return null;
        }
        return translationsModel.getBundle(Locale.forLanguageTag(userPreferences.getProperty("language-tag")));
    }

    @Override
    public void setPreference(String tag, String preferenceTag) {
        preferencesDao.setPreference(tag, preferenceTag);
    }


}
