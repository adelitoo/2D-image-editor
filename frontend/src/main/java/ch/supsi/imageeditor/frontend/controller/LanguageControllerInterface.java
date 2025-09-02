package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.LanguageModel;

import java.util.ResourceBundle;

public interface LanguageControllerInterface {
    LanguageModel getLanguageModel();
    ResourceBundle getResourceBundle();
    void setLanguageTagInsidePreferences(String s);
}
