package ch.supsi.imageeditor.backend.application;

import java.util.ResourceBundle;

public interface PreferencesControllerInterface {
    ResourceBundle getPreference();

    void setPreference(String tag, String preferenceTag);
}
