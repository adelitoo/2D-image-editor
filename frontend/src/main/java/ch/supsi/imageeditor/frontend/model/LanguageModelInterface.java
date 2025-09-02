package ch.supsi.imageeditor.frontend.model;

import java.util.ResourceBundle;

public interface LanguageModelInterface {
    ResourceBundle getResourceBundle();
    void setLanguageTagInsidePreferences(String s);
}
