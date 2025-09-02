package ch.supsi.imageeditor.backend.business;


import java.util.ResourceBundle;

public interface PreferencesBusinessInterface {

    ResourceBundle getPreference();

    void setPreference(String tag, String preferenceTag);

}
