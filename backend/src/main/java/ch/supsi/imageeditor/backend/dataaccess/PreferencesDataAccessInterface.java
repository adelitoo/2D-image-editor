package ch.supsi.imageeditor.backend.dataaccess;

import java.util.Properties;

public interface PreferencesDataAccessInterface {

    Properties getPreferences();

    void setPreference(String tag, String preferenceTag);
}
