package ch.supsi.imageeditor.backend.dataaccess;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {

    private static final String translationsResourceBundlePath = "languages.labels";

    private static final String supportedLanguagesPath = "/supported-languages.properties";

    public static TranslationsPropertiesDataAccess myself;

    protected TranslationsPropertiesDataAccess() {
    }

    // singleton instantiation of this data access object
    // guarantees only a single instance exists in the life of the application
    public static TranslationsPropertiesDataAccess getInstance() {
        if (myself == null) {
            myself = new TranslationsPropertiesDataAccess();
        }

        return myself;
    }
    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(translationsResourceBundlePath, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
    }
}
