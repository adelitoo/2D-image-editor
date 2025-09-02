package ch.supsi.imageeditor.backend.dataaccess;

import java.util.Locale;
import java.util.ResourceBundle;

public interface TranslationsDataAccessInterface {


    ResourceBundle getBundle(Locale locale);
}
